package com.daon.onjung.account.repository.mysql;

import com.daon.onjung.account.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreRepository extends JpaRepository <Store, Long> {
}
