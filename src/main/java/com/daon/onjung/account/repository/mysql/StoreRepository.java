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

    Optional<Store> findById(Long id);

    Optional<Store> findByOcrStoreNameAndOcrStoreAddress(String ocrStoreName, String ocrStoreAddress);

    @EntityGraph(attributePaths = {"onjungTags"})
    Optional<Store> findWithOnjungTagsById(Long id);

    @Query("SELECT s FROM Store s " +
            "LEFT JOIN s.onjungTags tag " +
            "WHERE (:title IS NULL OR s.title LIKE %:title%) " +
            "AND (:onjungTags IS NULL OR tag IN :onjungTags) " +
            "GROUP BY s.id " +
            "ORDER BY s.id ASC") // 정렬 기준을 ID로 유지하거나 원하는 기준으로 수정
    Page<Store> findStores(
            @Param("title") String title,
            @Param("onjungTags") List<EOnjungTag> onjungTags,
            Pageable pageable
    );

    @Query("SELECT COUNT(s) FROM Share s WHERE s.store.id = :storeId")
    long countSharesByStoreId(@Param("storeId") Long storeId);

    @Query("SELECT COUNT(d) FROM Donation d WHERE d.store.id = :storeId")
    long countDonationsByStoreId(@Param("storeId") Long storeId);

    @Query("SELECT COUNT(r) FROM Receipt r WHERE r.store.id = :storeId")
    long countReceiptsByStoreId(@Param("storeId") Long storeId);

    // Share, donation, receipt 에 포함된 중복된 값을 제외한 user의 수
    @Query("SELECT COUNT(DISTINCT u) FROM User u " +
            "WHERE EXISTS (SELECT 1 FROM Share s WHERE s.user.id = u.id AND s.store.id = :storeId) " +
            "   OR EXISTS (SELECT 1 FROM Donation d WHERE d.user.id = u.id AND d.store.id = :storeId) " +
            "   OR EXISTS (SELECT 1 FROM Receipt r WHERE r.user.id = u.id AND r.store.id = :storeId)")
    Integer countUsersByStoreId(@Param("storeId") Long storeId);

    // 총 Donation의 금액
    @Query("SELECT SUM(d.donationAmount) FROM Donation d WHERE d.store.id = :storeId")
    Integer sumDonationAmountByStoreId(@Param("storeId") Long storeId);

    // 총 Receipt의 금액
    @Query("SELECT SUM(r.paymentAmount) FROM Receipt r WHERE r.store.id = :storeId")
    Integer sumReceiptAmountByStoreId(@Param("storeId") Long storeId);

    // store id와 일치하는 share의 count에 100을 곱한 금액
    @Query("SELECT SUM(s.count * 100) FROM Share s WHERE s.store.id = :storeId")
    Integer sumShareAmountByStoreId(@Param("storeId") Long storeId);

}
