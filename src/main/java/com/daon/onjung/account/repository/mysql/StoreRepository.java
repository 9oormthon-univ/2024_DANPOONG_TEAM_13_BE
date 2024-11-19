package com.daon.onjung.account.repository.mysql;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.type.EOnjungTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository <Store, Long> {

    Optional<Store> findByOcrStoreNameAndOcrStoreAddress(String ocrStoreName, String ocrStoreAddress);

    @EntityGraph(attributePaths = {"onjungTags"})
    Optional<Store> findWithOnjungTagsById(Long id);

    @Query("SELECT s FROM Store s " +
            "LEFT JOIN s.events e " +
            "WHERE (:title IS NOT NULL OR s.title LIKE %:title%) " +
            "AND (:onjungTags IS NOT NULL OR EXISTS (" +
            "    SELECT 1 FROM s.onjungTags tag " +
            "    WHERE tag IN :onjungTags" +
            ")) " +
            "GROUP BY s.id " +
            "ORDER BY " +
            "CASE WHEN :sortByDonationCount = 'asc' THEN COUNT(e) END ASC, " +
            "CASE WHEN :sortByDonationCount = 'desc' THEN COUNT(e) END DESC")
    Page<Store> findStoresByDonationCountWithDirection(
            @Param("title") String title,
            @Param("onjungTags") List<EOnjungTag> onjungTags,
            @Param("sortByDonationCount") String sortByDonationCount,
            Pageable pageable
    );
}
