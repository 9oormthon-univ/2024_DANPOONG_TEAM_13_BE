package com.daon.onjung.core.utility;

import com.daon.onjung.core.dto.CreateVirtualAccountResponseDto;
import com.daon.onjung.core.dto.DepositOrTransferVirtualAccountResponseDto;
import com.daon.onjung.core.dto.ReadVirtualAccountResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;


@Component
public class BankUtil {

    @Value("${bank-api.url}")
    private String url;

    @Value("${bank-api.path}")
    private String path;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String createCreateVirtualAccountRequestUrl() {
        return UriComponentsBuilder.fromHttpUrl(url)
                .path(path)
                .toUriString();
    }

    public HttpHeaders createVirtualAccountRequestHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return headers;
    }

    public String createCreateVirtualAccountRequestBody(Long eventId, String bankName) {
        Map<String, Object> requestJson = Map.of(
                "event_id", eventId,
                "bank_name", bankName
        );
        try {
            return objectMapper.writeValueAsString(requestJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error creating JSON body", e);
        }
    }

    public String createDepositVirtualAccountRequestUrl(Long id) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .path(path + "/" + id + "/deposit")
                .toUriString();
    }

    public String createDepositVirtualAccountRequestBody(Integer amount, String senderName) {
        Map<String, Object> requestJson = Map.of(
                "amount", amount,
                "sender_name", senderName
        );
        try {
            return objectMapper.writeValueAsString(requestJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error creating JSON body", e);
        }
    }

    public String createTransferVirtualAccountRequestUrl(Long id) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .path(path + "/" + id + "/transfer")
                .toUriString();
    }

    public String createTransferVirtualAccountRequestBody(Integer amount, String targetAccountNumber) {
        Map<String, Object> requestJson = Map.of(
                "target_account_number", targetAccountNumber,
                "amount", amount
        );
        try {
            return objectMapper.writeValueAsString(requestJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error creating JSON body", e);
        }
    }

    public String createReadVirtualAccountRequestUrl(Long id) {
        return UriComponentsBuilder.fromHttpUrl(url)
                .path(path + "/" + id)
                .toUriString();
    }

    public CreateVirtualAccountResponseDto mapToCreateVirtualAccountResponseDto(JSONObject jsonObject) {
        try {
            return objectMapper.readValue(jsonObject.toJSONString(), CreateVirtualAccountResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error mapping JSON to CreateVirtualAccountResponseDto", e);
        }
    }

    public DepositOrTransferVirtualAccountResponseDto mapToDepositOrTransferVirtualAccountResponseDto(JSONObject jsonObject) {
        try {
            return objectMapper.readValue(jsonObject.toJSONString(), DepositOrTransferVirtualAccountResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error mapping JSON to DepositOrTransferVirtualAccountResponseDto", e);
        }
    }

    public ReadVirtualAccountResponseDto mapToReadVirtualAccountResponseDto(JSONObject jsonObject) {
        try {
            return objectMapper.readValue(jsonObject.toJSONString(), ReadVirtualAccountResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error mapping JSON to ReadVirtualAccountResponseDto", e);
        }
    }

}
