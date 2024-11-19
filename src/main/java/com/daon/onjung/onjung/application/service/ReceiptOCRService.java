package com.daon.onjung.onjung.application.service;

import com.daon.onjung.core.utility.OCRUtil;
import com.daon.onjung.core.utility.RestClientUtil;
import com.daon.onjung.onjung.application.dto.response.ReceiptOCRResponseDto;
import com.daon.onjung.onjung.application.usecase.ReceiptOCRUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ReceiptOCRService implements ReceiptOCRUseCase {

    private final OCRUtil ocrUtil;
    private final RestClientUtil restClientUtil;

    @Override
    public ReceiptOCRResponseDto execute(MultipartFile file) {
        String url = ocrUtil.createOCRRequestUrl();
        HttpHeaders headers = ocrUtil.createOCRRequestHeaders();
        MultiValueMap<String, Object> body = ocrUtil.createOCRRequestBody(file);

        try {
            return ReceiptOCRResponseDto.fromReceiptOCRDto(
                    ocrUtil.mapToReceiptOCRDto(
                            restClientUtil.sendMultipartFormDataPostMethod(url, headers, body)
                    )
            );
        } catch (Exception e) {
            throw new RuntimeException("OCR 요청 중 오류가 발생했습니다. {}", e);
        }
    }
}
