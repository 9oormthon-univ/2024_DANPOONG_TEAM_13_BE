package com.daon.onjung.event.repository.mysql;

import com.daon.onjung.event.domain.mysql.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    // 가게의 이벤트들 중에, 현재 모금중인 이벤트 하나를 반환
    @Query("SELECT e FROM Event e WHERE e.store.id = :storeId AND e.status = 'IN_PROGRESS'")
    Optional<Event> findINPROGRESSEventByStoreId(@Param("storeId") Long storeId);
}
