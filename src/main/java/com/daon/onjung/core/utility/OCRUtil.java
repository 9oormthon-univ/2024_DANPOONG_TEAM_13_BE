package com.daon.onjung.core.utility;

import com.daon.onjung.core.dto.ReceiptOCRDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class OCRUtil {

    @Value("${naver-cloud.ocr.url}")
    private String ocrUrl;

    @Value("${naver-cloud.ocr.path}")
    private String ocrPath;

    @Value("${naver-cloud.ocr.key}")
    private String ocrKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String createOCRRequestUrl() {
        return UriComponentsBuilder.fromHttpUrl(ocrUrl)
                .path(ocrPath)
                .toUriString();
    }

    public HttpHeaders createOCRRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-OCR-SECRET", ocrKey);
        headers.setContentType(org.springframework.http.MediaType.MULTIPART_FORM_DATA);
        return headers;
    }

    public MultiValueMap<String, Object> createOCRRequestBody(MultipartFile imageFile) {
        try {
            Map<String, Object> requestJson = Map.of(
                    "images", List.of(Map.of(
                            "format", "jpg",
                            "name", "demo"
                    )),
                    "requestId", UUID.randomUUID().toString(),
                    "version", "V2",
                    "timestamp", System.currentTimeMillis()
            );

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            body.add("message", new JSONObject(requestJson).toString());
            body.add("file", new ByteArrayResource(imageFile.getBytes()) {
                @Override
                public String getFilename() {
                    return imageFile.getOriginalFilename();
                }
            });
            return body;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create request body: {}", e);
        }
    }

    public ReceiptOCRDto mapToReceiptOCRDto(JSONObject jsonObject) {
        try {
            return objectMapper.readValue(jsonObject.toString(), ReceiptOCRDto.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to map to ReceiptOCRDto: {}", e);
        }
    }
}
