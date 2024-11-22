package com.daon.onjung.security.repository.mysql;

import com.daon.onjung.event.domain.Ticket;
import com.daon.onjung.security.domain.mysql.Account;
import com.daon.onjung.security.domain.type.ESecurityProvider;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface AccountRepository extends JpaRepository<Account, UUID> {

    Optional<Account> findBySerialId(String serialId);

    Optional<Account> findBySerialIdAndProvider(String serialId, ESecurityProvider provider);

    @Query("SELECT a.password FROM Ticket t " +
            "JOIN t.store s " +               // Ticket과 Store를 조인
            "JOIN s.owner a " +               // Store와 Account를 조인
            "WHERE t = :ticket")              // Ticket 객체 자체를 기준으로 조회
    Optional<String> findPasswordByTicket(@Param("ticket") Ticket ticket);
}
