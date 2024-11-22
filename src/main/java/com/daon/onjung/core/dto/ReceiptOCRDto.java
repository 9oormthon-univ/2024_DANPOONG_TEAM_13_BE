package com.daon.onjung.core.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ReceiptOCRDto(

        @JsonProperty("version")
        String version,

        @JsonProperty("requestId")
        String requestId,

        @JsonProperty("timestamp")
        Long timestamp,

        @JsonProperty("images")
        List<Image> images

) {

    public record Image(
            @JsonProperty("uid")
            String uid,

            @JsonProperty("name")
            String name,

            @JsonProperty("inferResult")
            String inferResult,

            @JsonProperty("message")
            String message,

            @JsonProperty("validationResult")
            ValidationResult validationResult,

            @JsonProperty("receipt")
            Receipt receipt
    ) {}

    public record ValidationResult(
            @JsonProperty("result")
            String result
    ) {}

    public record Receipt(
            @JsonProperty("meta")
            Meta meta,

            @JsonProperty("result")
            Result result
    ) {}

    public record Meta(
            @JsonProperty("estimatedLanguage")
            String estimatedLanguage
    ) {}

    public record Result(
            @JsonProperty("storeInfo")
            StoreInfo storeInfo,

            @JsonProperty("paymentInfo")
            PaymentInfo paymentInfo,

            @JsonProperty("subResults")
            List<SubResult> subResults,

            @JsonProperty("totalPrice")
            Price totalPrice,

            @JsonProperty("subTotal")
            List<TaxPrice> subTotal
    ) {}

    public record StoreInfo(
            @JsonProperty("name")
            TextBlock name,

            @JsonProperty("bizNum")
            TextBlock bizNum,

            @JsonProperty("addresses")
            List<TextBlock> addresses,

            @JsonProperty("tel")
            List<TextBlock> tel,

            @JsonProperty("subName")
            TextBlock subName
    ) {}


    public record PaymentInfo(
            @JsonProperty("date")
            DateInfo date,

            @JsonProperty("time")
            TimeInfo time,

            @JsonProperty("cardInfo")
            CardInfo cardInfo,

            @JsonProperty("confirmNum")
            TextBlock confirmNum
    ) {}

    public record DateInfo(
            @JsonProperty("text")
            String text,

            @JsonProperty("formatted")
            FormattedDate formatted,

            @JsonProperty("keyText")
            String keyText,

            @JsonProperty("confidenceScore")
            Double confidenceScore,

            @JsonProperty("boundingPolys")
            List<Polygon> boundingPolys,

            @JsonProperty("maskingPolys")
            List<Polygon> maskingPolys
    ) {}


    public record TimeInfo(
            @JsonProperty("text")
            String text,

            @JsonProperty("formatted")
            FormattedTime formatted,

            @JsonProperty("keyText")
            String keyText,

            @JsonProperty("confidenceScore")
            Double confidenceScore,

            @JsonProperty("boundingPolys")
            List<Polygon> boundingPolys,

            @JsonProperty("maskingPolys")
            List<Polygon> maskingPolys
    ) {}


    public record FormattedDate(
            @JsonProperty("year")
            String year,

            @JsonProperty("month")
            String month,

            @JsonProperty("day")
            String day
    ) {}

    public record FormattedTime(
            @JsonProperty("hour")
            String hour,

            @JsonProperty("minute")
            String minute,

            @JsonProperty("second")
            String second
    ) {}

    public record CardInfo(
            @JsonProperty("company")
            TextBlock company,

            @JsonProperty("number")
            TextBlock number
    ) {}

    public record SubResult(
            @JsonProperty("items")
            List<Item> items
    ) {}

    public record Item(
            @JsonProperty("name")
            TextBlock name,

            @JsonProperty("count")
            TextBlock count,

            @JsonProperty("price")
            Price price
    ) {}

    public record Price(
            @JsonProperty("price")
            TextBlock price,

            @JsonProperty("unitPrice")
            TextBlock unitPrice
    ) {}


    public record TaxPrice(
            @JsonProperty("taxPrice")
            List<TextBlock> taxPrice,

            @JsonProperty("discountPrice")
            List<TextBlock> discountPrice
    ) {}



    public record TextBlock(
            @JsonProperty("text")
            String text,

            @JsonProperty("formatted")
            FormattedText formatted,

            @JsonProperty("keyText")
            String keyText,

            @JsonProperty("confidenceScore")
            Double confidenceScore,

            @JsonProperty("boundingPolys")
            List<Polygon> boundingPolys,

            @JsonProperty("maskingPolys")
            List<Polygon> maskingPolys
    ) {}

    public record Polygon(
            @JsonProperty("vertices")
            List<Vertex> vertices
    ) {}

    public record Vertex(
            @JsonProperty("x")
            Double x,

            @JsonProperty("y")
            Double y
    ) {}

    public record FormattedText(
            @JsonProperty("value")
            String value
    ) {}
}
