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


    @Query("SELECT COUNT(s) FROM Share s WHERE s.store.id = :storeId")
    long countSharesByStoreId(@Param("storeId") Long storeId);

    @Query("SELECT COUNT(d) FROM Donation d WHERE d.store.id = :storeId")
    long countDonationsByStoreId(@Param("storeId") Long storeId);

    @Query("SELECT COUNT(r) FROM Receipt r WHERE r.store.id = :storeId")
    long countReceiptsByStoreId(@Param("storeId") Long storeId);
}
