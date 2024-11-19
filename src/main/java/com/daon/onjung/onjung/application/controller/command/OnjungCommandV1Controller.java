package com.daon.onjung.onjung.application.controller.command;

import com.daon.onjung.core.dto.ResponseDto;
import com.daon.onjung.onjung.application.dto.response.ReceiptOCRResponseDto;
import com.daon.onjung.onjung.application.usecase.ReceiptOCRUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class OnjungCommandV1Controller {

    private final ReceiptOCRUseCase receiptOCRUseCase;

    /**
     * 영수증 OCR 처리하기
     */
    @PostMapping(value = "/api/v1/receipts/ocr", consumes = "multipart/form-data")
    public ResponseDto<ReceiptOCRResponseDto> OCRReceipt(
            @RequestPart(value = "file") MultipartFile file
    ) {
        return ResponseDto.ok(receiptOCRUseCase.execute(file));
    }
}
