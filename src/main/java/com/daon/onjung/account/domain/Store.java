package com.daon.onjung.account.domain;

import com.daon.onjung.account.domain.type.ECategory;
import com.daon.onjung.account.domain.type.EOnjungTag;
import com.daon.onjung.event.domain.mysql.Event;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "stores")
public class Store {
    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */

    @Column(name = "banner_img_url", length = 2080, nullable = false)
    private String bannerImgUrl;

    @ElementCollection(targetClass = EOnjungTag.class)
    @CollectionTable(name = "store_onjung_tags", joinColumns = @JoinColumn(name = "store_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "onjung_tags")
    private Set<EOnjungTag> onjungTags = new HashSet<>();

    @Column(name = "title", length = 100, nullable = false)
    private String title;

    @Column(name = "youtube_url", length = 2080, nullable = false)
    private String youtubeUrl;

    @Column(name = "logo_img_url", length = 2080, nullable = false)
    private String logoImgUrl;

    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "category")
    private ECategory category;

    @Column(name = "introduction", length = 2000, nullable = false)
    private String introduction;

    @Column(name = "ocr_store_name", length = 50, nullable = false)
    private String ocrStoreName;

    @Column(name = "ocr_store_address", length = 100, nullable = false)
    private String ocrStoreAddress;

    /* -------------------------------------------- */
    /* Timestamp Column --------------------------- */
    /* -------------------------------------------- */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Event> events;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreHistory> storeHistories;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Store(String title, String bannerImgUrl, Set<EOnjungTag> onjungTags, String youtubeUrl, String logoImgUrl, String name, ECategory category, String introduction, String ocrStoreName, String ocrStoreAddress, Owner owner) {
       this.title = title;
       this.onjungTags = onjungTags;
       this.bannerImgUrl = bannerImgUrl;
       this.youtubeUrl = youtubeUrl;
       this.logoImgUrl = logoImgUrl;
       this.name = name;
       this.category = category;
       this.introduction = introduction;
       this.ocrStoreName = ocrStoreName;
       this.ocrStoreAddress = ocrStoreAddress;
       this.createdAt = LocalDateTime.now();
       this.owner = owner;
    }

    // get tags
    public List<String> getTags() {
        return onjungTags.stream()
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
