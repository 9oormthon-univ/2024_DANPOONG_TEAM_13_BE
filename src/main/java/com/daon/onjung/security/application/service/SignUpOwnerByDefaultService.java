package com.daon.onjung.security.application.service;

import com.daon.onjung.account.domain.Owner;
import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.service.OwnerService;
import com.daon.onjung.account.domain.service.StoreService;
import com.daon.onjung.account.domain.type.EBankName;
import com.daon.onjung.account.repository.mysql.OwnerRepository;
import com.daon.onjung.account.repository.mysql.StoreRepository;
import com.daon.onjung.event.domain.service.ScheduledEventJobService;
import com.daon.onjung.core.dto.CreateVirtualAccountResponseDto;
import com.daon.onjung.core.utility.BankUtil;
import com.daon.onjung.core.utility.RestClientUtil;
import com.daon.onjung.core.utility.S3Util;
import com.daon.onjung.event.domain.mysql.Event;
import com.daon.onjung.event.domain.service.EventService;
import com.daon.onjung.event.repository.mysql.EventRepository;
import com.daon.onjung.security.application.dto.request.SignUpOwnerByDefaultRequestDto;
import com.daon.onjung.security.application.usecase.SignUpOwnerByDefaultUseCase;
import com.daon.onjung.security.domain.type.EImageType;
import com.daon.onjung.security.domain.type.ESecurityProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpHeaders;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignUpOwnerByDefaultService implements SignUpOwnerByDefaultUseCase {

    private final OwnerRepository ownerRepository;
    private final StoreRepository storeRepository;
    private final EventRepository eventRepository;

    private final OwnerService ownerService;
    private final StoreService storeService;
    private final EventService eventService;
    private final ScheduledEventJobService scheduledEventJobService;

    private final S3Util s3Util;
    private final BankUtil bankUtil;
    private final RestClientUtil restClientUtil;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    @Transactional
    public void execute(MultipartFile logo,
                        MultipartFile banner,
                        SignUpOwnerByDefaultRequestDto requestDto
    ) {

        // 오너 생성
        Owner owner = ownerService.createOwner(
                ESecurityProvider.DEFAULT,
                requestDto.serialId(),
                bCryptPasswordEncoder.encode(requestDto.password()),
                requestDto.bankInfo().name(),
                requestDto.bankInfo().accountNumber(),
                true
        );

        ownerRepository.save(owner);

        // 로고 이미지 저장
        String logo_img_url = s3Util.uploadImageFile(logo, requestDto.serialId(), EImageType.LOGO_IMG);

        // 배너 이미지 저장
        String banner_img_url = s3Util.uploadImageFile(banner, requestDto.serialId(), EImageType.BANNER_IMG);

        // 가게 정보 생성
        Store store = storeService.createStore(
                requestDto.storeInfo().title(),
                banner_img_url,
                requestDto.storeInfo().onjungTag(),
                requestDto.storeInfo().youtubeUrl(),
                logo_img_url,
                requestDto.storeInfo().name(),
                requestDto.storeInfo().category(),
                requestDto.storeInfo().introduction(),
                requestDto.ocrInfo().storeName(),
                requestDto.ocrInfo().addressName(),
                owner
        );
        storeRepository.save(store);

        // 생성한 가게에 대한 이벤트 생성
        Event event = eventService.createEvent(
                LocalDate.now(),
                LocalDate.now().plusDays(13),
                store
        );
        event = eventRepository.save(event);

        // 가상계좌 생성
        String url = bankUtil.createCreateVirtualAccountRequestUrl();
        HttpHeaders headers = bankUtil.createVirtualAccountRequestHeaders();
        String body = bankUtil.createCreateVirtualAccountRequestBody(event.getId(), EBankName.KAKAO.toString());

        CreateVirtualAccountResponseDto createVirtualAccountResponseDto =
                bankUtil.mapToCreateVirtualAccountResponseDto(restClientUtil.sendPostMethod(url, headers, body));

        // 이벤트에 은행 정보 업데이트
        event.updateBankInfo(
                EBankName.fromString(createVirtualAccountResponseDto.data().bankName()),
                createVirtualAccountResponseDto.data().bankId()
        );
        eventRepository.save(event);

        // 생성한 이벤트에 대해 이벤트 발행
        applicationEventPublisher.publishEvent(
                scheduledEventJobService.createScheduledJob(
                        event.getId(),
                        event.getEndDate().plusDays(1).atStartOfDay()
//                        LocalDateTime.now().plusMinutes(1) // 테스트용 1분 뒤
                )
        );

    }
}
