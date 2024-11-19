package com.daon.onjung.onjung.application.controller.command;

import com.daon.onjung.core.annotation.security.AccountID;
import com.daon.onjung.core.dto.ResponseDto;
import com.daon.onjung.onjung.application.dto.request.CreateReceiptRequestDto;
import com.daon.onjung.onjung.application.dto.response.ReceiptOCRResponseDto;
import com.daon.onjung.onjung.application.usecase.CreateReceiptUseCase;
import com.daon.onjung.onjung.application.usecase.ReceiptOCRUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class OnjungCommandV1Controller {

    private final ReceiptOCRUseCase receiptOCRUseCase;
    private final CreateReceiptUseCase createReceiptUseCase;

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

}
