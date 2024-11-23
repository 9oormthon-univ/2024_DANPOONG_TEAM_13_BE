package com.daon.onjung.onjung.application.service;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.StoreRepository;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.core.utility.BankUtil;
import com.daon.onjung.core.utility.RestClientUtil;
import com.daon.onjung.event.domain.mysql.Event;
import com.daon.onjung.event.repository.mysql.EventRepository;
import com.daon.onjung.onjung.application.dto.request.CreateDonationRequestDto;
import com.daon.onjung.onjung.application.dto.response.CreateDonationResponseDto;
import com.daon.onjung.onjung.application.usecase.CreateDonationUseCase;
import com.daon.onjung.onjung.domain.Donation;
import com.daon.onjung.onjung.domain.service.DonationService;
import com.daon.onjung.onjung.repository.mysql.DonationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateDonationService implements CreateDonationUseCase {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final DonationRepository donationRepository;
    private final EventRepository eventRepository;

    private final DonationService donationService;

    private final RestClientUtil restClientUtil;
    private final BankUtil bankUtil;

    @Override
    @Transactional
    public CreateDonationResponseDto execute(UUID accountId, Long storeId, CreateDonationRequestDto requestDto) {

        // 유저 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 가게 조회
        Store store = storeRepository.findWithOnjungTagsById(storeId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 이벤트 조회
        Event event = eventRepository.findINPROGRESSEventByStoreId(storeId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 동참 생성
        Donation donation = donationService.createDonation(user, store, event, requestDto.donationAmount());
        donationRepository.save(donation);

        // 해당 이벤트에 생성된 가상 계좌가 있는지 확인
        // TODO: Domain Service 로직으로 이동
        if (event.getBankId() == null) {
            throw new CommonException(ErrorCode.NOT_FOUND_RESOURCE);
        }

        // 은행에 동참금액 입금
        String url = bankUtil.createDepositVirtualAccountRequestUrl(event.getBankId());
        HttpHeaders headers = bankUtil.createVirtualAccountRequestHeaders();
        String body = bankUtil.createDepositVirtualAccountRequestBody(requestDto.donationAmount(), user.getNickName());

        restClientUtil.sendPostMethod(url, headers, body);

        return CreateDonationResponseDto.of(donation, store);
    }

}
