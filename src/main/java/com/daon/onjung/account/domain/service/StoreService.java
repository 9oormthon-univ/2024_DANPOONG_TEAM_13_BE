package com.daon.onjung.account.domain.service;

import com.daon.onjung.account.domain.Owner;
import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.type.ECategory;
import com.daon.onjung.account.domain.type.EOnjungTag;
import org.springframework.stereotype.Service;

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
            String ocrStoreAddress
            , Owner owner) {
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
                .owner(owner)
                .build();
    }
}
