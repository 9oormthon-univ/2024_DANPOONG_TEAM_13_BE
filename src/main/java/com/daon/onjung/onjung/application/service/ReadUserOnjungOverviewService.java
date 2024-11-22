package com.daon.onjung.onjung.application.service;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.onjung.application.dto.response.ReadUserOnjungOverviewResponseDto;
import com.daon.onjung.onjung.application.usecase.ReadUserOnjungOverviewUseCase;
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
public class ReadUserOnjungOverviewService implements ReadUserOnjungOverviewUseCase {

    private final UserRepository userRepository;
    private final DonationRepository donationRepository;
    private final ShareRepository shareRepository;
    private final ReceiptRepository receiptRepository;

    private final OnjungService onjungService;

    @Override
    @Transactional(readOnly = true)
    public ReadUserOnjungOverviewResponseDto execute(
            UUID accountId) {

        // 유저 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 유저의 동참, 공유, 영수증 인증 조회
        List<Donation> donations = donationRepository.findAllByUser(user);
        List<Share> shares = shareRepository.findAllByUser(user);
        List<Receipt> receipts = receiptRepository.findAllByUser(user);

        Onjung onjung = onjungService.createOnjung(donations, receipts, shares);

        // 생성 시간 기준으로 정렬
        List<Object> sortedOnjungByCreatedAt = onjungService.sortOnjungByCreatedAt(onjung);

        return ReadUserOnjungOverviewResponseDto.of(sortedOnjungByCreatedAt);
    }
}
