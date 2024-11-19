package com.daon.onjung.onjung.application.dto.response;

import com.daon.onjung.core.dto.ReceiptOCRDto;
import com.daon.onjung.core.dto.SelfValidating;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.core.utility.DateTimeUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public class ReceiptOCRResponseDto extends SelfValidating<ReceiptOCRDto> {

    @JsonProperty("store_name")
    private final String storeName;

    @JsonProperty("store_address")
    private final String storeAddress;

    @JsonProperty("payment_date")
    private final String paymentDate;

    @JsonProperty("payment_amount")
    private final String paymentAmount;

    @Builder
    public ReceiptOCRResponseDto(String storeName, String storeAddress, String paymentDate, String paymentAmount) {
        this.storeName = storeName;
        this.storeAddress = storeAddress;
        this.paymentDate = paymentDate;
        this.paymentAmount = paymentAmount;

        this.validateSelf();
    }

    public static ReceiptOCRResponseDto fromReceiptOCRDto(ReceiptOCRDto receiptOCRDto) {
        if (receiptOCRDto == null) {
            return ReceiptOCRResponseDto.builder()
                    .storeName(null)
                    .storeAddress(null)
                    .paymentDate(null)
                    .paymentAmount(null)
                    .build();
        }

        String rawDate = receiptOCRDto.images().get(0).receipt().result().paymentInfo().date().text();
        String paymentDate = DateTimeUtil.formatDateString(rawDate);

        try {
            return ReceiptOCRResponseDto.builder()
                    .storeName(receiptOCRDto.images().get(0).receipt().result().storeInfo().name().text())
                    .storeAddress(receiptOCRDto.images().get(0).receipt().result().storeInfo().addresses().get(0).text())
                    .paymentDate(paymentDate)
                    .paymentAmount(receiptOCRDto.images().get(0).receipt().result().totalPrice().price().text())
                    .build();
        } catch (Exception e) {
            throw new CommonException(ErrorCode.OCR_RECOGNITION_ERROR);
        }
    }
}
