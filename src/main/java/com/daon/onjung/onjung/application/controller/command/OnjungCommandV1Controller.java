package com.daon.onjung.onjung.application.controller.command;

import com.daon.onjung.core.annotation.security.AccountID;
import com.daon.onjung.core.dto.ResponseDto;
import com.daon.onjung.onjung.application.dto.request.CreateDonationRequestDto;
import com.daon.onjung.onjung.application.dto.request.CreateReceiptRequestDto;
import com.daon.onjung.onjung.application.dto.response.CreateDonationResponseDto;
import com.daon.onjung.onjung.application.dto.response.ReceiptOCRResponseDto;
import com.daon.onjung.onjung.application.usecase.CreateDonationUseCase;
import com.daon.onjung.onjung.application.usecase.CreateOrUpdateShareUseCase;
import com.daon.onjung.onjung.application.usecase.CreateReceiptUseCase;
import com.daon.onjung.onjung.application.usecase.ReceiptOCRUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OnjungCommandV1Controller {

    private final ReceiptOCRUseCase receiptOCRUseCase;
    private final CreateReceiptUseCase createReceiptUseCase;
    private final CreateOrUpdateShareUseCase createOrUpdateShareUseCase;
    private final CreateDonationUseCase createDonationUseCase;

    /**
     * 4.4 가게 방문 인증용 영수증 OCR 조회하기
     */
    @PostMapping(value = "/api/v1/receipts/ocr", consumes = "multipart/form-data")
    public ResponseDto<ReceiptOCRResponseDto> OCRReceipt(
            @RequestPart(value = "file") MultipartFile file
    ) {
        return ResponseDto.ok(receiptOCRUseCase.execute(file));
    }

    /**
     * 4.5 영수증 리뷰 등록하기
     */
    @PostMapping("/api/v1/receipts")
    public ResponseDto<Void> reviewReceipt(
            @AccountID UUID accountId,
            @RequestBody @Valid CreateReceiptRequestDto requestDto
    ) {
        createReceiptUseCase.execute(accountId, requestDto);
        return ResponseDto.created(null);
    }

    /**
     * 4.6 온기 공유하기
     */
    @PutMapping("/api/v1/stores/{id}/shares")
    public ResponseDto<Void> shareOnjung(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        if (createOrUpdateShareUseCase.execute(accountId, id)) {
            return ResponseDto.created(null);
        }
        return ResponseDto.ok(null);
    }

    /**
     * 4.7 가게 후원 동참하기
     */
    @PostMapping("/api/v1/stores/{id}/donations")
    public ResponseDto<CreateDonationResponseDto> donateStore(
            @AccountID UUID accountId,
            @PathVariable Long id,
            @RequestBody @Valid CreateDonationRequestDto requestDto
    ) {
        return ResponseDto.created(createDonationUseCase.execute(accountId, id, requestDto));
    }

}
