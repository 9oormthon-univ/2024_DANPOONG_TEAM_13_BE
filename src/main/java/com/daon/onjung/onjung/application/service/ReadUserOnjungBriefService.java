package com.daon.onjung.onjung.application.service;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.onjung.application.dto.response.ReadUserOnjungBriefResponseDto;
import com.daon.onjung.onjung.application.usecase.ReadUserOnjungBriefUseCase;
import com.daon.onjung.onjung.domain.Donation;
import com.daon.onjung.onjung.domain.Onjung;
import com.daon.onjung.onjung.domain.Receipt;
import com.daon.onjung.onjung.domain.Share;
import com.daon.onjung.onjung.domain.service.OnjungService;
import com.daon.onjung.onjung.repository.mysql.DonationRepository;
import com.daon.onjung.onjung.repository.mysql.ReceiptRepository;
import com.daon.onjung.onjung.repository.mysql.ShareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserOnjungBriefService implements ReadUserOnjungBriefUseCase {

    private final UserRepository userRepository;
    private final DonationRepository donationRepository;
    private final ShareRepository shareRepository;
    private final ReceiptRepository receiptRepository;

    private final OnjungService onjungService;

    @Override
    @Transactional(readOnly = true)
    public ReadUserOnjungBriefResponseDto execute(UUID accountId) {

        // 유저 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 유저의 동참, 공유, 영수증인증 조회
        List<Donation> donations = donationRepository.findAllByUser(user);
        List<Share> shares = shareRepository.findAllByUser(user);
        List<Receipt> receipts = receiptRepository.findAllByUser(user);

        // 온정 객체 생성
        Onjung onjung = onjungService.createOnjung(donations, receipts, shares);

        // 온정 객체를 통해 가게 중복을 제외한 가게 리스트 조회
        List<String> storeList = onjungService.getUniqueStoreList(onjung).stream()
                .map(Store::getName)
                .toList();

        // 가게 리스트가 5개 이상이면 remainCount를 계산하여 반환
        Integer remainCount = 0;
        if(storeList.size() >= 5) {
            remainCount = storeList.size() - 5;
        }

        return ReadUserOnjungBriefResponseDto.of(storeList, remainCount);
    }
}
