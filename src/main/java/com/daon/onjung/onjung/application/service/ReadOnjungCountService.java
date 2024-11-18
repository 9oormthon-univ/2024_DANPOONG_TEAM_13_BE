package com.daon.onjung.onjung.application.service;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.onjung.application.dto.response.ReadOnjungCountResponseDto;
import com.daon.onjung.onjung.application.usecase.ReadOnjungCountUseCase;
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
public class ReadOnjungCountService implements ReadOnjungCountUseCase {

    private final UserRepository userRepository;
    private final DonationRepository donationRepository;
    private final ReceiptRepository receiptRepository;
    private final ShareRepository shareRepository;

    private final OnjungService onjungService;

    @Override
    @Transactional(readOnly = true)
    public ReadOnjungCountResponseDto execute(
            UUID accountId
    ) {

        // User 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // User의 모든 동참 내역 조회
        List<Donation> donations = donationRepository.findAllByUser(user);

        // User의 모든 영수증 인증 내역 조회
        List<Receipt> receipts = receiptRepository.findAllByUser(user);

        // User의 모든 공유 내역 조회
        List<Share> shares = shareRepository.findAllByUser(user);

        // Onjung 생성
        Onjung onjung = onjungService.createOnjung(donations, receipts, shares);

        // User의 총 온정 횟수 조회
        Integer totalOnjungCount = onjungService.calculateTotalOnjungCount(onjung);

        return ReadOnjungCountResponseDto.builder()
                .totalOnjungCount(totalOnjungCount)
                .build();
    }
}
