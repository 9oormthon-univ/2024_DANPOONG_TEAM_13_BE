package com.daon.onjung.account.application.dto.response;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.core.dto.SelfValidating;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Getter
public class ReadStoreOverviewsResponseDto extends SelfValidating<ReadStoreOverviewsResponseDto> {

    @NotNull(message = "has_next는 null일 수 없습니다.")
    @JsonProperty("has_next")
    private final Boolean hasNext;

    @JsonProperty("store_list")
    private final List<StoreOverviewDto> storeList;

    @Builder
    public ReadStoreOverviewsResponseDto(
            Boolean hasNext,
            List<StoreOverviewDto> storeList
    ) {
        this.hasNext = hasNext;
        this.storeList = storeList;
    }

    public static ReadStoreOverviewsResponseDto fromEntity(
            List<StoreOverviewDto> storeList,
            boolean hasNext
    ) {
        return ReadStoreOverviewsResponseDto.builder()
                .hasNext(hasNext)
                .storeList(storeList)
                .build();
    }

    @Getter
    public static class StoreOverviewDto {

        @NotNull(message = "id는 null일 수 없습니다.")
        @JsonProperty("id")
        private final Long id;

        @NotNull(message = "tags는 null일 수 없습니다.")
        @JsonProperty("tags")
        private final List<String> tags;

        @NotNull(message = "title은 null일 수 없습니다.")
        @JsonProperty("title")
        private final String title;

        @NotNull(message = "banner_img_url은 null일 수 없습니다.")
        @JsonProperty("banner_img_url")
        private final String bannerImgUrl;

        @NotNull(message = "name은 null일 수 없습니다.")
        @JsonProperty("name")
        private final String name;

        @NotNull(message = "address은 null일 수 없습니다.")
        @JsonProperty("address")
        private final String address;

        @NotNull(message = "total_onjung_count은 null일 수 없습니다.")
        @JsonProperty("total_onjung_count")
        private final Integer totalOnjungCount;

        @Builder
        public StoreOverviewDto(
                Long id,
                List<String> tags,
                String title,
                String bannerImgUrl,
                String name,
                String address,
                Integer totalOnjungCount
        ) {
            this.id = id;
            this.tags = tags;
            this.title = title;
            this.bannerImgUrl = bannerImgUrl;
            this.name = name;
            this.address = address;
            this.totalOnjungCount = totalOnjungCount;
        }

        public static StoreOverviewDto fromEntity(Store store, Integer totalOnjungCount) {
            return StoreOverviewDto.builder()
                    .id(store.getId())
                    .tags(store.getTags())
                    .title(store.getTitle())
                    .bannerImgUrl(store.getBannerImgUrl())
                    .name(store.getName())
                    .address(store.getOcrStoreAddress())
                    .totalOnjungCount(totalOnjungCount)
                    .build();
        }

    }
}

