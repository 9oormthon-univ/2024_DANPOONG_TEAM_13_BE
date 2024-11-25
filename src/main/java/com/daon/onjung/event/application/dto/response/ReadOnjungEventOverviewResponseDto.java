package com.daon.onjung.event.application.dto.response;

import com.daon.onjung.core.dto.SelfValidating;
import com.daon.onjung.event.domain.type.EStatus;
import com.daon.onjung.onjung.domain.type.EOnjungType;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadOnjungEventOverviewResponseDto extends SelfValidating<ReadOnjungEventOverviewResponseDto> {

    @NotNull(message = "has_next는 null일 수 없습니다.")
    @JsonProperty("has_next")
    private final Boolean hasNext;

    @JsonProperty("event_list")
    private final List<EventDto> eventDtos;

    @Builder
    public ReadOnjungEventOverviewResponseDto(
            Boolean hasNext,
            List<EventDto> eventDtos
    ) {
        this.hasNext = hasNext;
        this.eventDtos = eventDtos;
    }

    public static ReadOnjungEventOverviewResponseDto fromPage(
            List<EventDto> eventDtos,
            boolean hasNext
    ) {

        return ReadOnjungEventOverviewResponseDto.builder()
                .hasNext(hasNext)
                .eventDtos(eventDtos)
                .build();
    }


    @Getter
    public static class EventDto {

        @JsonProperty("store_info")
        private final StoreInfoDto storeInfo;

        @NotNull(message = "created_date은 null일 수 없습니다.")
        @JsonProperty("created_date")
        private final String createdDate;

        @NotNull(message = "onjung_type은 null일 수 없습니다.")
        @JsonProperty("onjung_type")
        private final EOnjungType onjungType;

        @NotNull(message = "status는 null일 수 없습니다.")
        @JsonProperty("status")
        private final EStatus status;

        @JsonProperty("event_period")
        private final String eventPeriod;

        @JsonProperty("store_delivery_date")
        private final String storeDeliveryDate;

        @JsonProperty("ticket_issue_date")
        private final String ticketIssueDate;

        @JsonProperty("report_date")
        private final String reportDate;

        @Builder
        public EventDto(
                StoreInfoDto storeInfo,
                String createdDate,
                EOnjungType onjungType,
                EStatus status,
                String eventPeriod,
                String storeDeliveryDate,
                String ticketIssueDate,
                String reportDate
        ) {
            this.storeInfo = storeInfo;
            this.createdDate = createdDate;
            this.onjungType = onjungType;
            this.status = status;
            this.eventPeriod = eventPeriod;
            this.storeDeliveryDate = storeDeliveryDate;
            this.ticketIssueDate = ticketIssueDate;
            this.reportDate = reportDate;
        }

        public static EventDto fromEntity(
                StoreInfoDto storeInfo,
                String createdDate,
                EOnjungType onjungType,
                EStatus status,
                String eventPeriod,
                String storeDeliveryDate,
                String ticketIssueDate,
                String reportDate
        ) {

            return EventDto.builder()
                    .storeInfo(storeInfo)
                    .createdDate(createdDate)
                    .onjungType(onjungType)
                    .status(status)
                    .eventPeriod(eventPeriod)
                    .storeDeliveryDate(storeDeliveryDate)
                    .ticketIssueDate(ticketIssueDate)
                    .reportDate(reportDate)
                    .build();
        }


        @Getter
        public static class StoreInfoDto {

            @NotNull
            @JsonProperty("id")
            private final Long id;

            @NotNull(message = "logo_img_url은 null일 수 없습니다.")
            @JsonProperty("logo_img_url")
            private final String logoImgUrl;

            @NotNull(message = "title은 null일 수 없습니다.")
            @JsonProperty("title")
            private final String title;

            @NotNull(message = "name는 null일 수 없습니다.")
            @JsonProperty("name")
            private final String name;

            @Builder
            public StoreInfoDto(
                    Long id,
                    String logoImgUrl,
                    String title,
                    String name
            ) {
                this.id = id;
                this.logoImgUrl = logoImgUrl;
                this.title = title;
                this.name = name;
            }

            public static StoreInfoDto fromEntity(
                    Long id,
                    String logoImgUrl,
                    String title,
                    String name
            ) {

                return StoreInfoDto.builder()
                        .id(id)
                        .logoImgUrl(logoImgUrl)
                        .title(title)
                        .name(name)
                        .build();
            }
        }
    }
}
