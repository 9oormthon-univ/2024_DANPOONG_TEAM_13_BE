package com.daon.onjung.security.application.dto.request;

import com.daon.onjung.account.domain.type.EBankName;
import com.daon.onjung.account.domain.type.ECategory;
import com.daon.onjung.account.domain.type.EOnjungTag;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record SignUpOwnerByDefaultRequestDto(

        @NotNull
        @Size(min = 1, max = 20)
        @JsonProperty("serial_id")
        String serialId,

        @NotNull
        @Size(min = 6, max = 320)
        @JsonProperty("password")
        String password,

        @NotNull
        @JsonProperty("store_info")
        StoreInfo storeInfo,

        @NotNull
        @JsonProperty("ocr_info")
        OcrInfo ocrInfo,

        @NotNull
        @JsonProperty("bank_info")
        BankInfo bankInfo

) {

    public record StoreInfo(
            @JsonProperty("onjung_tag")
            Set<EOnjungTag> onjungTag,

            @NotNull
            @Size(min = 1, max = 30)
            @JsonProperty("title")
            String title,

            @JsonProperty("youtube_url")
            String youtubeUrl,

            @NotNull
            @Size(min = 1, max = 15)
            @JsonProperty("name")
            String name,

            @NotNull
            @JsonProperty("category")
            ECategory category,

            @NotNull
            @Size(min = 1, max = 100)
            @JsonProperty("introduction")
            String introduction,

            @NotNull
            @JsonProperty("latitude")
            Double latitude,

            @NotNull
            @JsonProperty("longitude")
            Double longitude
    ) {}

    public record OcrInfo(
            @NotNull
            @Size(min = 1, max = 50)
            @JsonProperty("store_name")
            String storeName,

            @NotNull
            @Size(min = 1, max = 100)
            @JsonProperty("address_name")
            String addressName
    ) {}

    public record BankInfo(
            @NotNull
            @JsonProperty("name")
            EBankName name,

            @NotNull
            @Size(min = 10, max = 20)
            @JsonProperty("account_number")
            String accountNumber
    ) {}
}
