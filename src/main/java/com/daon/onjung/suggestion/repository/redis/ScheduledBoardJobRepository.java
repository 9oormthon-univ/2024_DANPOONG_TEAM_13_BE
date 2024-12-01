package com.daon.onjung.suggestion.repository.redis;

import com.daon.onjung.suggestion.domain.redis.ScheduledBoardJob;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ScheduledBoardJobRepository extends CrudRepository<ScheduledBoardJob, String> {

    @NotNull List<ScheduledBoardJob> findAll();

    Optional<ScheduledBoardJob> findByBoardId(Long boardId);
}
