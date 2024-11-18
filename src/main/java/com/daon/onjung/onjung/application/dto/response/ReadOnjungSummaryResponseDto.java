package com.daon.onjung.onjung.application.dto.response;

import com.daon.onjung.core.dto.SelfValidating;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadOnjungSummaryResponseDto extends SelfValidating<ReadOnjungSummaryResponseDto> {

    @JsonProperty("date_time")
    private final String dateTime;

    @JsonProperty("total_donation_count")
    private final Integer totalDonationCount;

    @JsonProperty("total_donator_count")
    private final Integer totalDonatorCount;

    @Builder
    public ReadOnjungSummaryResponseDto(String dateTime, Integer totalDonationCount, Integer totalDonatorCount) {
        this.dateTime = dateTime;
        this.totalDonationCount = totalDonationCount;
        this.totalDonatorCount = totalDonatorCount;

        this.validateSelf();
    }

}
