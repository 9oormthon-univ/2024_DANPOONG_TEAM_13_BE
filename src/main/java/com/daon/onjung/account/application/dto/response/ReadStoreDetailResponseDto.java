package com.daon.onjung.account.application.dto.response;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.StoreHistory;
import com.daon.onjung.core.dto.SelfValidating;
import com.daon.onjung.core.utility.DateTimeUtil;
import lombok.Builder;
import lombok.Getter;
import jakarta.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@Getter
public class ReadStoreDetailResponseDto extends SelfValidating<ReadStoreOverviewsResponseDto> {

    @JsonProperty("store_info")
    private final StoreInfoDto storeInfo;

    @JsonProperty("event_info")
    private final EventInfoDto eventInfo;

    @JsonProperty("onjung_info")
    private final OnjungInfoDto onjungInfo;

    @JsonProperty("store_histories")
    private final List<StoreHistoryDto> storeHistories;

    @Builder
    public ReadStoreDetailResponseDto(
            StoreInfoDto storeInfo,
            EventInfoDto eventInfo,
            OnjungInfoDto onjungInfo,
            List<StoreHistoryDto> storeHistories
    ) {
        this.storeInfo = storeInfo;
        this.eventInfo = eventInfo;
        this.onjungInfo = onjungInfo;
        this.storeHistories = storeHistories;
    }

    public static ReadStoreDetailResponseDto fromEntity(
            StoreInfoDto store,
            EventInfoDto event,
            OnjungInfoDto onjung,
            List<StoreHistoryDto> storeHistories
    ) {
        return ReadStoreDetailResponseDto.builder()
                .storeInfo(store)
                .eventInfo(event)
                .onjungInfo(onjung)
                .storeHistories(storeHistories)
                .build();
    }

    @Getter
    public static class StoreInfoDto {

        @NotNull(message = "banner_img_url은 null일 수 없습니다.")
        @JsonProperty("banner_img_url")
        private final String bannerImgUrl;

        @NotNull(message = "tag는 null일 수 없습니다.")
        @JsonProperty("tag")
        private final List<String> tags;

        @NotNull(message = "title은 null일 수 없습니다.")
        @JsonProperty("title")
        private final String title;

        @JsonProperty("youtube_url")
        private final String youtubeUrl;

        @NotNull(message = "logo_img_url은 null일 수 없습니다.")
        @JsonProperty("logo_img_url")
        private final String logoImgUrl;

        @NotNull(message = "name은 null일 수 없습니다.")
        @JsonProperty("name")
        private final String name;

        @NotNull(message = "category는 null일 수 없습니다.")
        @JsonProperty("category")
        private final String category;

        @NotNull(message = "address는 null일 수 없습니다.")
        @JsonProperty("address")
        private final String address;

        @JsonProperty("introduction")
        private final String introduction;

        @Builder
        public StoreInfoDto(
                String bannerImgUrl,
                List<String> tags,
                String title,
                String youtubeUrl,
                String logoImgUrl,
                String name,
                String category,
                String address,
                String introduction
        ) {
            this.bannerImgUrl = bannerImgUrl;
            this.tags = tags;
            this.title = title;
            this.youtubeUrl = youtubeUrl;
            this.logoImgUrl = logoImgUrl;
            this.name = name;
            this.category = category;
            this.address = address;
            this.introduction = introduction;
        }

        public static StoreInfoDto fromEntity(Store store) {
            return StoreInfoDto.builder()
                    .bannerImgUrl(store.getBannerImgUrl())
                    .tags(store.getTags())
                    .title(store.getTitle())
                    .youtubeUrl(store.getYoutubeUrl())
                    .logoImgUrl(store.getLogoImgUrl())
                    .name(store.getName())
                    .category(store.getCategory().name())
                    .address(store.getOcrStoreAddress())
                    .introduction(store.getIntroduction())
                    .build();
        }
    }

    @Getter
    public static class EventInfoDto {

        @NotNull(message = "total_amount는 null일 수 없습니다.")
        @JsonProperty("total_amount")
        private final Integer totalAmount;

        @NotNull(message = "rest_of_date는 null일 수 없습니다.")
        @JsonProperty("rest_of_date")
        private final Integer restOfDate;

        @Builder
        public EventInfoDto(Integer totalAmount, Integer restOfDate) {
            this.totalAmount = totalAmount;
            this.restOfDate = restOfDate;
        }

        public static EventInfoDto of(Integer totalAmount, Integer restOfDate) {
            return EventInfoDto.builder()
                    .totalAmount(totalAmount)
                    .restOfDate(restOfDate)
                    .build();
        }
    }

    @Getter
    public static class OnjungInfoDto {

        @NotNull(message = "total_donator_count는 null일 수 없습니다.")
        @JsonProperty("total_donator_count")
        private final Integer totalDonatorCount;

        @NotNull(message = "total_donation_amount는 null일 수 없습니다.")
        @JsonProperty("total_donation_amount")
        private final Integer totalDonationAmount;

        @NotNull(message = "total_receipt_amount는 null일 수 없습니다.")
        @JsonProperty("total_receipt_amount")
        private final Integer totalReceiptAmount;

        @NotNull(message = "total_shared_amount는 null일 수 없습니다.")
        @JsonProperty("total_shared_amount")
        private final Integer totalSharedAmount;

        @Builder
        public OnjungInfoDto(
                Integer totalDonatorCount,
                Integer totalDonationAmount,
                Integer totalReceiptAmount,
                Integer totalSharedAmount
        ) {
            this.totalDonatorCount = totalDonatorCount;
            this.totalDonationAmount = totalDonationAmount;
            this.totalReceiptAmount = totalReceiptAmount;
            this.totalSharedAmount = totalSharedAmount;
        }

        public static OnjungInfoDto fromEntity(
                Integer totalDonatorCount,
                Integer totalDonationAmount,
                Integer totalReceiptAmount,
                Integer totalSharedAmount
        ) {
            return OnjungInfoDto.builder()
                    .totalDonatorCount(totalDonatorCount)
                    .totalDonationAmount(totalDonationAmount)
                    .totalReceiptAmount(totalReceiptAmount)
                    .totalSharedAmount(totalSharedAmount)
                    .build();
        }
    }

    @Getter
    public static class StoreHistoryDto {

        @NotNull(message = "date는 null일 수 없습니다.")
        @JsonProperty("date")
        private final String date;

        @NotNull(message = "info는 null일 수 없습니다.")
        @JsonProperty("info")
        private final List<StoreHistoryInfo> storeHistoryInfo;

        @Builder
        public StoreHistoryDto(String date, List<StoreHistoryInfo> storeHistoryInfo) {
            this.date = date;
            this.storeHistoryInfo = storeHistoryInfo;
        }

        public static StoreHistoryDto fromEntity(StoreHistory storeHistory) {
            return StoreHistoryDto.builder()
                    .date(DateTimeUtil.convertLocalDateToString(storeHistory.getActionDate()))
                    .storeHistoryInfo(
                            List.of(
                                    StoreHistoryInfo.fromEntity(
                                            storeHistory.getContent(),
                                            storeHistory.getAmount() / 10000 + "만원"
                                    )
                            )
                    )
                    .build();
        }


        @Getter
        public static class StoreHistoryInfo {

            @NotNull(message = "content는 null일 수 없습니다.")
            @JsonProperty("content")
            private final String content;

            @NotNull(message = "amount는 null일 수 없습니다.")
            @JsonProperty("amount")
            private final String amount;

            @Builder
            public StoreHistoryInfo(String content, String amount) {
                this.content = content;
                this.amount = amount;
            }

            public static StoreHistoryInfo fromEntity(String content, String amount) {
                return StoreHistoryInfo.builder()
                        .content(content)
                        .amount(amount)
                        .build();
            }
        }
    }
}
