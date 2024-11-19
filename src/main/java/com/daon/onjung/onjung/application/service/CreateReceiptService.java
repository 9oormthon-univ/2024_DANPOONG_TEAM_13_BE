package com.daon.onjung.onjung.application.service;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.StoreRepository;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.core.utility.DateTimeUtil;
import com.daon.onjung.onjung.application.dto.request.CreateReceiptRequestDto;
import com.daon.onjung.onjung.application.usecase.CreateReceiptUseCase;
import com.daon.onjung.onjung.domain.Receipt;
import com.daon.onjung.onjung.domain.service.ReceiptService;
import com.daon.onjung.onjung.repository.mysql.ReceiptRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateReceiptService implements CreateReceiptUseCase {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReceiptRepository receiptRepository;

    private final ReceiptService receiptService;

    @Override
    @Transactional
    public void execute(UUID accountId, CreateReceiptRequestDto requestDto) {

        // 유저 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 매장 조회
        Store store = storeRepository.findByOcrStoreNameAndOcrStoreAddress(requestDto.storeName(), requestDto.storeAddress())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_MATCHED_STORE));

        // paymentDate 형변환
        LocalDate paymentDate = DateTimeUtil.convertStringToLocalDate(requestDto.paymentDate());

        // paymentAmount 쉼표 제거 및 형변환
        Integer paymentAmount = Integer.parseInt(requestDto.paymentAmount().replaceAll(",", ""));

        // 영수증 생성
        Receipt receipt = receiptRepository.save(receiptService.createReceipt(
                paymentDate,
                paymentAmount,
                user,
                store
        ));
        receiptRepository.save(receipt);

    }
}
