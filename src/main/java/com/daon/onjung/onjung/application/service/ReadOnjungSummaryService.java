package com.daon.onjung.onjung.application.service;

import com.daon.onjung.core.utility.DateTimeUtil;
import com.daon.onjung.onjung.application.dto.response.ReadOnjungSummaryResponseDto;
import com.daon.onjung.onjung.application.usecase.ReadOnjungSummaryUseCase;
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

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadOnjungSummaryService implements ReadOnjungSummaryUseCase {

    private final DonationRepository donationRepository;
    private final ReceiptRepository receiptRepository;
    private final ShareRepository shareRepository;

    private final OnjungService onjungService;

    @Override
    @Transactional(readOnly = true)
    public ReadOnjungSummaryResponseDto execute() {

        // 전체 후원금, 영수증인증, 공유 조회
        List<Donation> donations = donationRepository.findAll();
        List<Receipt> receipts = receiptRepository.findAll();
        List<Share> shares = shareRepository.findAll();

        // 온정 생성
        Onjung onjung = onjungService.createOnjung(donations, receipts, shares);

        // 총 온기전달 횟수 계산
        Integer totalOnjungCount = onjungService.calculateTotalOnjungCount(onjung);

        // 총 함께한 온정인 수 계산
        Integer totalUniqueOnjungUserCount = onjungService.calculateTotalUniqueOnjungUserCount(onjung);

        return ReadOnjungSummaryResponseDto.builder()
                .totalDonationCount(totalOnjungCount)
                .totalDonatorCount(totalUniqueOnjungUserCount)
                .dateTime(DateTimeUtil.convertLocalDateTimeToCustomDateTime(LocalDateTime.now()))
                .build();
    }
}
