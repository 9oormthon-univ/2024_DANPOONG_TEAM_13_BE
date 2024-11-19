package com.daon.onjung.account.repository.mysql;

import com.daon.onjung.account.domain.Store;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoreRepository extends JpaRepository <Store, Long> {

    Optional<Store> findByOcrStoreNameAndOcrStoreAddress(String ocrStoreName, String ocrStoreAddress);

    @EntityGraph(attributePaths = {"onjungTags"})
    Optional<Store> findWithOnjungTagsById(Long id);
}
