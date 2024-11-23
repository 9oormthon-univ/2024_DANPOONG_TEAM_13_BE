package com.daon.onjung.onjung.application.dto.response;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.type.EOnjungTag;
import com.daon.onjung.core.dto.SelfValidating;
import com.daon.onjung.core.utility.DateTimeUtil;
import com.daon.onjung.onjung.domain.Donation;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.Set;

@Getter
public class CreateDonationResponseDto extends SelfValidating<CreateDonationResponseDto> {

    @JsonProperty("donation_amount")
    private final Integer donationAmount;

    @JsonProperty("issue_date")
    private final String issueDate;

    @JsonProperty("store_info")
    private final StoreInfoDto storeInfo;

    @Builder
    public CreateDonationResponseDto(Integer donationAmount, String issueDate, StoreInfoDto storeInfo) {
        this.donationAmount = donationAmount;
        this.issueDate = issueDate;
        this.storeInfo = storeInfo;
        this.validateSelf();
    }

    @Getter
    public static class StoreInfoDto {

        @JsonProperty("logo_img_url")
        private final String logoImgUrl;

        @JsonProperty("tag")
        private final Set<EOnjungTag> tag;

        @JsonProperty("name")
        private final String name;

        @Builder
        public StoreInfoDto(String logoImgUrl, Set<EOnjungTag> tag, String name) {
            this.logoImgUrl = logoImgUrl;
            this.tag = tag;
            this.name = name;
        }
    }

    public static CreateDonationResponseDto of(Donation donation, Store store) {
        return CreateDonationResponseDto.builder()
                .donationAmount(donation.getDonationAmount())
                .issueDate(DateTimeUtil.convertLocalDateTimeToDotSeparatedDateTime(donation.getCreatedAt()))
                .storeInfo(StoreInfoDto.builder()
                        .logoImgUrl(store.getLogoImgUrl())
                        .tag(store.getOnjungTags())
                        .name(store.getName())
                        .build())
                .build();
    }
}
