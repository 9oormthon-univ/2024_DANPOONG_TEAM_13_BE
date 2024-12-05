package com.daon.onjung.account.domain.service;

import com.daon.onjung.account.domain.Owner;
import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.type.ECategory;
import com.daon.onjung.account.domain.type.EOnjungTag;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@Service
public class StoreService {

    public Store createStore(
            String title,
            String bannerImgUrl,
            Set<EOnjungTag> onjungTags,
            String youtubeUrl,
            String logoImgUrl,
            String name,
            ECategory category,
            String introduction,
            String ocrStoreName,
            String ocrStoreAddress,
            Double latitude,
            Double longitude,
            Owner owner) {
        return Store.builder()
                .title(title)
                .bannerImgUrl(bannerImgUrl)
                .onjungTags(onjungTags)
                .youtubeUrl(youtubeUrl)
                .logoImgUrl(logoImgUrl)
                .name(name)
                .category(category)
                .introduction(introduction)
                .ocrStoreName(ocrStoreName)
                .ocrStoreAddress(ocrStoreAddress)
                .latitude(latitude)
                .longitude(longitude)
                .owner(owner)
                .build();
    }

    // title null 처리 메서드
    public String convertToTitle(String title) {
        if (title != null && title.isBlank()) {
            return null;
        }
        return title;
    }

    // 필터 파라미터 변환 메서드
    public List<EOnjungTag> convertToOnjungTags(String onjungTags) {
        return parseEnums(onjungTags, EOnjungTag.class);
    }


    // enum 타입 파싱 메서드
    private <E extends Enum<E>> List<E> parseEnums(String input, Class<E> enumClass) {
        if (input == null || input.isEmpty()) return List.of();
        List<E> result;
        try {
            result = Arrays.stream(input.split(","))
                    .map(String::trim)
                    .map(value -> Enum.valueOf(enumClass, value))
                    .toList();
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid enum value provided: " + input, e);
        }
        return result;
    }

}
