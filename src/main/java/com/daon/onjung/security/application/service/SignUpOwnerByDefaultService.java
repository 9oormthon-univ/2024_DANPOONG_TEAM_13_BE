package com.daon.onjung.security.application.service;

import com.daon.onjung.account.domain.Owner;
import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.service.OwnerService;
import com.daon.onjung.account.domain.service.StoreService;
import com.daon.onjung.account.repository.mysql.OwnerRepository;
import com.daon.onjung.account.repository.mysql.StoreRepository;
import com.daon.onjung.core.utility.S3Util;
import com.daon.onjung.security.application.dto.request.SignUpOwnerByDefaultRequestDto;
import com.daon.onjung.security.application.usecase.SignUpOwnerByDefaultUseCase;
import com.daon.onjung.security.domain.type.EImageType;
import com.daon.onjung.security.domain.type.ESecurityProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class SignUpOwnerByDefaultService implements SignUpOwnerByDefaultUseCase {

    private final OwnerRepository ownerRepository;

    private final OwnerService ownerService;
    private final StoreService storeService;

    private final S3Util s3Util;
    private final StoreRepository storeRepository;

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
                requestDto.password(),
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

    }
}
