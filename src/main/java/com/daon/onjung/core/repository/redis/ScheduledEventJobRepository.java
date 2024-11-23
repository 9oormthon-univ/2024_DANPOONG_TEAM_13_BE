package com.daon.onjung.core.repository.redis;

import com.daon.onjung.core.domain.ScheduledEventJob;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduledEventJobRepository extends CrudRepository<ScheduledEventJob, String> {
    @NotNull List<ScheduledEventJob> findAll();

    void deleteByEventId(Long eventId);
}
