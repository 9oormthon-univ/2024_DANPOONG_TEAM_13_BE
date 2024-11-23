package com.daon.onjung.account.repository.mysql;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.StoreHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreHistoryRepository extends JpaRepository<StoreHistory, Long> {
    List<StoreHistory> findByStoreOrderByActionDateDesc(Store store);
}
