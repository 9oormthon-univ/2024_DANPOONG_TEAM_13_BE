package com.daon.onjung.event.repository.mysql;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.event.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    // 가게의 이벤트들 중에, 현재 모금중인 이벤트 하나를 반환
    @Query("SELECT e FROM Event e WHERE e.store.id = :storeId AND e.status = 'IN_PROGRESS'")
    Optional<Event> findINPROGRESSEventByStoreId(@Param("storeId") Long storeId);

    // 가게와 localdate로 event의 start date와 end date 사이에 있는 이벤트를 반환
    @Query("SELECT e FROM Event e WHERE e.store.id = :store AND e.startDate <= :date AND e.endDate >= :date")
    Optional<Event> findEventByStoreAndLocalDate(@Param("store") Long storeId, @Param("date") LocalDate date);

}
