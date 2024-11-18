package com.daon.onjung.account.repository.mysql;

import com.daon.onjung.account.domain.StoreHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreHistoryRepository extends JpaRepository<StoreHistory, Long> {
}
