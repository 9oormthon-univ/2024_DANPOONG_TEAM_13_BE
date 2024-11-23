package com.daon.onjung.event.application.dto.response;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.core.dto.SelfValidating;
import com.daon.onjung.core.utility.DateTimeUtil;
import com.daon.onjung.event.domain.Ticket;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ReadTicketResponseDto extends SelfValidating<ReadTicketResponseDto> {

    @NotNull(message = "has_next는 null일 수 없습니다.")
    @JsonProperty("has_next")
    private final Boolean hasNext;

    @JsonProperty("ticket_list")
    private final List<TicketDto> ticketDtos;

    @Builder
    public ReadTicketResponseDto(
            Boolean hasNext,
            List<TicketDto> ticketDtos
    ) {
        this.hasNext = hasNext;
        this.ticketDtos = ticketDtos;
    }

    public static ReadTicketResponseDto fromPage(
            List<TicketDto> ticketDtos,
            boolean hasNext
    ) {

        return ReadTicketResponseDto.builder()
                .hasNext(hasNext)
                .ticketDtos(ticketDtos)
                .build();
    }

    public static class TicketDto {

        @NotNull(message = "id는 null일 수 없습니다.")
        @JsonProperty("id")
        private final Long id;

        @JsonProperty("store_info")
        private final StoreInfoDto storeInfo;

        @JsonProperty("expiration_date")
        private final String expirationDate;

        @JsonProperty("ticket_price")
        private final Integer ticketPrice;

        @JsonProperty("is_validate")
        private final Boolean isValidate;

        @Builder
        public TicketDto(
                Long id,
                StoreInfoDto storeInfo,
                String expirationDate,
                Integer ticketPrice,
                Boolean isValidate
        ) {
            this.id = id;
            this.storeInfo = storeInfo;
            this.expirationDate = expirationDate;
            this.ticketPrice = ticketPrice;
            this.isValidate = isValidate;
        }

        public static TicketDto fromEntity(Ticket ticket, Store store) {
            return TicketDto.builder()
                    .id(ticket.getId())
                    .storeInfo(StoreInfoDto.fromEntity(store))
                    .expirationDate(DateTimeUtil.convertLocalDateToDotSeparatedDateTime(ticket.getExpirationDate()))
                    .ticketPrice(ticket.getTicketPrice())
                    .isValidate(ticket.getIsValidate())
                    .build();
        }
    }


    @Getter
    public static class StoreInfoDto {

        @NotNull(message = "logo_img_url은 null일 수 없습니다.")
        @JsonProperty("logo_img_url")
        private final String logoImgUrl;

        @NotNull(message = "name은 null일 수 없습니다.")
        @JsonProperty("name")
        private final String name;

        @NotNull(message = "category은 null일 수 없습니다.")
        @JsonProperty("category")
        private final String category;

        @NotNull(message = "address은 null일 수 없습니다.")
        @JsonProperty("address")
        private final String address;

        @Builder
        public StoreInfoDto(
                String logoImgUrl,
                String name,
                String category,
                String address
        ) {
            this.logoImgUrl = logoImgUrl;
            this.name = name;
            this.category = category;
            this.address = address;
        }

        public static StoreInfoDto fromEntity(Store store) {
            return StoreInfoDto.builder()
                    .logoImgUrl(store.getLogoImgUrl())
                    .name(store.getName())
                    .category(store.getCategory().name())
                    .address(store.getOcrStoreAddress())
                    .build();
        }

    }
}
