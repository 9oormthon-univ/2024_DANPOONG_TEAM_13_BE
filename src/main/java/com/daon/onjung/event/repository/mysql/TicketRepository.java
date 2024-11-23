package com.daon.onjung.event.repository.mysql;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.event.domain.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // user로 Ticket 조회, expirationDate를 ASC로 정렬
    Page<Ticket> findByUserOrderByExpirationDateAsc(User user, Pageable pageable);

    // user로 Ticket 수 조회
    Long countByUser(User user);

}
