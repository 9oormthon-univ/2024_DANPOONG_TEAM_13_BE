package com.daon.onjung.onjung.application.dto.response;

import com.daon.onjung.core.dto.SelfValidating;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.core.utility.DateTimeUtil;
import com.daon.onjung.onjung.domain.Donation;
import com.daon.onjung.onjung.domain.Receipt;
import com.daon.onjung.onjung.domain.Share;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ReadUserOnjungOverviewResponseDto extends SelfValidating<ReadUserOnjungOverviewResponseDto> {

    @JsonProperty("store_list")
    @NotNull(message = "store_list는 null일 수 없습니다.")
    private final List<StoreDto> storeList;

    @Builder
    public ReadUserOnjungOverviewResponseDto(List<StoreDto> storeList) {
        this.storeList = storeList;
        this.validateSelf();
    }

    @Getter
    public static class StoreDto extends SelfValidating<StoreDto> {

        @JsonProperty("onjung_type")
        @NotNull(message = "onjung_type은 null일 수 없습니다.")
        private final String onjungType;

        @JsonProperty("store_name")
        @NotNull(message = "store_name은 null일 수 없습니다.")
        private final String storeName;

        @JsonProperty("store_title")
        @NotNull(message = "store_title은 null일 수 없습니다.")
        private final String storeTitle;

        @JsonProperty("amount")
        @NotNull(message = "amount는 null일 수 없습니다.")
        private final Integer amount;

        @JsonProperty("date")
        @NotNull(message = "date는 null일 수 없습니다.")
        private final String date;

        @Builder
        public StoreDto(String onjungType, String storeName, String storeTitle, Integer amount, String date) {
            this.onjungType = onjungType;
            this.storeName = storeName;
            this.storeTitle = storeTitle;
            this.amount = amount;
            this.date = date;
            this.validateSelf();
        }

        public static StoreDto of(String onjungType, String storeName, String storeTitle, Integer amount, String date) {
            return StoreDto.builder()
                    .onjungType(onjungType)
                    .storeName(storeName)
                    .storeTitle(storeTitle)
                    .amount(amount)
                    .date(date)
                    .build();
        }
    }

    public static ReadUserOnjungOverviewResponseDto of(List<Object> sortedEntities) {

        List<StoreDto> storeList = sortedEntities.stream()
                .map(entity -> {
                    if (entity instanceof Donation donation) {
                        return StoreDto.of(
                                "DONATION",
                                donation.getStore().getName(),
                                donation.getStore().getTitle(),
                                donation.getDonationAmount(),
                                DateTimeUtil.convertLocalDateTimeToDotSeparatedDateTime(donation.getCreatedAt())
                        );
                    } else if (entity instanceof Receipt receipt) {
                        return StoreDto.of(
                                "RECEIPT",
                                receipt.getStore().getName(),
                                receipt.getStore().getTitle(),
                                receipt.getPaymentAmount(),
                                DateTimeUtil.convertLocalDateTimeToDotSeparatedDateTime(receipt.getCreatedAt())
                        );
                    } else if (entity instanceof Share share) {
                        return StoreDto.of(
                                "SHARE",
                                share.getStore().getName(),
                                share.getStore().getTitle(),
                                share.getCount() * 100,
                                DateTimeUtil.convertLocalDateTimeToDotSeparatedDateTime(share.getCreatedAt().atStartOfDay())
                        );
                    }
                    throw new CommonException(ErrorCode.INVALID_ARGUMENT);
                })
                .collect(Collectors.toList());

        return ReadUserOnjungOverviewResponseDto.builder()
                .storeList(storeList)
                .build();
    }
}
