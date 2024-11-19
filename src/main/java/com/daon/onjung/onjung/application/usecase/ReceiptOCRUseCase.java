package com.daon.onjung.onjung.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.onjung.application.dto.response.ReceiptOCRResponseDto;
import org.springframework.web.multipart.MultipartFile;

@UseCase
public interface ReceiptOCRUseCase {
    ReceiptOCRResponseDto execute(MultipartFile file);
}
