package com.daon.onjung.account.repository.mysql;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.type.EOnjungTag;
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
            "LEFT JOIN s.events e ON e.endDate = ( " +
            "   SELECT MIN(e1.endDate) FROM Event e1 " +
            "   WHERE e1.store.id = s.id AND e1.endDate > CURRENT_DATE" +
            ") " +
            "LEFT JOIN s.onjungTags tag " +
            "WHERE (:title IS NULL OR s.title LIKE %:title%) " +
            "AND (:onjungTags IS NULL OR tag IN :onjungTags) " +
            "GROUP BY s.id " +
            "ORDER BY MIN(e.endDate) ASC")
    List<Store> findStoresByEarliestEventOrdered(
            @Param("title") String title,
            @Param("onjungTags") List<EOnjungTag> onjungTags
    );

    @Query("SELECT COUNT(s) FROM Share s WHERE s.store.id = :storeId")
    long countSharesByStoreId(@Param("storeId") Long storeId);

    @Query("SELECT COUNT(d) FROM Donation d WHERE d.store.id = :storeId")
    long countDonationsByStoreId(@Param("storeId") Long storeId);

    @Query("SELECT COUNT(r) FROM Receipt r WHERE r.store.id = :storeId")
    long countReceiptsByStoreId(@Param("storeId") Long storeId);

    // Share, donation, receipt 에 포함된 중복된 값을 제외한 user의 수
    @Query("SELECT COUNT(DISTINCT u) FROM User u " +
            "JOIN Share s ON u.id = s.user.id " +
            "JOIN Donation d ON u.id = d.user.id " +
            "JOIN Receipt r ON u.id = r.user.id " +
            "WHERE s.store.id = :storeId OR d.store.id = :storeId OR r.store.id = :storeId")
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
