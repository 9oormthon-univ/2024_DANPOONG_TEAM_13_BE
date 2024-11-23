package com.daon.onjung.account.repository.mysql;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.StoreHistory;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreHistoryRepository extends JpaRepository<StoreHistory, Long> {
    @Query("SELECT sh FROM StoreHistory sh WHERE sh.store = :store ORDER BY sh.actionDate DESC")
    List<StoreHistory> findByStoreSortedByActionDate(@Param("store") Store store);
}
