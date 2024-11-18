package com.daon.onjung.onjung.application.dto.response;

import com.daon.onjung.core.dto.SelfValidating;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ReadOnjungBriefResponseDto extends SelfValidating<ReadOnjungBriefResponseDto> {

    @JsonProperty("total_onjung_count")
    private final Integer totalOnjungCount;

    @JsonProperty("total_onjung_amount")
    private final Integer totalOnjungAmount;

    @Builder
    public ReadOnjungBriefResponseDto(Integer totalOnjungCount, Integer totalOnjungAmount) {
        this.totalOnjungCount = totalOnjungCount;
        this.totalOnjungAmount = totalOnjungAmount;

        this.validateSelf();
    }
}
