package com.daon.onjung.company.application.dto.response;

import com.daon.onjung.core.dto.SelfValidating;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadCompanyBriefResponseDto extends SelfValidating<ReadCompanyBriefResponseDto> {

    @JsonProperty("company_images")
    private final List<String> companyImages;

    @Builder
    public ReadCompanyBriefResponseDto(
            List<String> companyImages
    ) {
        this.companyImages = companyImages;
    }

    public static ReadCompanyBriefResponseDto fromEntity(
            List<String> companyImages
    ) {
        return ReadCompanyBriefResponseDto.builder()
                .companyImages(companyImages)
                .build();
    }
}
