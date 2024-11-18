package com.daon.onjung.onjung.application.dto.response;

import com.daon.onjung.core.dto.SelfValidating;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public class ReadOnjungCountResponseDto extends SelfValidating<ReadOnjungCountResponseDto> {

    @JsonProperty("total_onjung_count")
    private final Integer totalOnjungCount;

    @Builder
    public ReadOnjungCountResponseDto(Integer totalOnjungCount) {
        this.totalOnjungCount = totalOnjungCount;
        this.validateSelf();
    }
}
