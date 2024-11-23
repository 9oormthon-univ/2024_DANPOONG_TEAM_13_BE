package com.daon.onjung.onjung.application.dto.response;

import com.daon.onjung.core.dto.SelfValidating;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadUserOnjungBriefResponseDto extends SelfValidating<ReadUserOnjungBriefResponseDto> {

    @NotNull(message = "store_name_list는 null일 수 없습니다.")
    @JsonProperty("store_name_list")
    private final List<String> storeNameList;

    @NotNull(message = "remain_count는 null일 수 없습니다.")
    @JsonProperty("remain_count")
    private final Integer remainCount;

    @Builder
    public ReadUserOnjungBriefResponseDto(List<String> storeNameList, Integer remainCount) {
        this.storeNameList = storeNameList;
        this.remainCount = remainCount;
        this.validateSelf();
    }

    public static ReadUserOnjungBriefResponseDto of(List<String> storeNameList, Integer remainCount) {
        return ReadUserOnjungBriefResponseDto.builder()
                .storeNameList(storeNameList)
                .remainCount(remainCount)
                .build();
    }
}
