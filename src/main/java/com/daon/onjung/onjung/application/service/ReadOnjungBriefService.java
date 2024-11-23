package com.daon.onjung.onjung.application.service;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.onjung.application.dto.response.ReadOnjungBriefResponseDto;
import com.daon.onjung.onjung.application.usecase.ReadOnjungBriefUseCase;
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
public class ReadOnjungBriefService implements ReadOnjungBriefUseCase {

    private final UserRepository userRepository;
    private final DonationRepository donationRepository;
    private final ReceiptRepository receiptRepository;
    private final ShareRepository shareRepository;

    private final OnjungService onjungService;

    @Override
    @Transactional(readOnly = true)
    public ReadOnjungBriefResponseDto execute(UUID accountId) {

        // 유저 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 유저의 기부 내역 전체 조회
        List<Donation> donations = donationRepository.findAllByUser(user);

        // 유저의 영수증 인증 내역 전체 조회
        List<Receipt> receipts = receiptRepository.findAllByUser(user);

        // 유저의 공유 내역 전체 조회
        List<Share> shares = shareRepository.findAllByUser(user);

        // 온정 생성
        Onjung onjung = onjungService.createOnjung(donations, receipts, shares);

        // 온정 총 개수 계산
        Integer totalOnjungCount = donations.size() + receipts.size() + shares.stream().mapToInt(Share::getCount).sum();

        // 온정 총 금액 계산
        Integer totalOnjungAmount = onjungService.calculateTotalOnjungAmount(onjung);

        return ReadOnjungBriefResponseDto.builder()
                .totalOnjungCount(totalOnjungCount)
                .totalOnjungAmount(totalOnjungAmount)
                .build();
    }

}
