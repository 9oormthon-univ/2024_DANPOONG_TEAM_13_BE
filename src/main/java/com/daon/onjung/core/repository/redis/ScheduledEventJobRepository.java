package com.daon.onjung.core.repository.redis;

import com.daon.onjung.core.domain.ScheduledEventJob;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduledEventJobRepository extends CrudRepository<ScheduledEventJob, String> {
    @NotNull List<ScheduledEventJob> findAll();

    Optional<ScheduledEventJob> findByEventId(Long eventId);
}
