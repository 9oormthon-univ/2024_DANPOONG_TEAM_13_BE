package com.daon.onjung.suggestion.domain.service;

import com.daon.onjung.suggestion.domain.redis.ScheduledBoardJob;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduledBoardJobService {

    public ScheduledBoardJob createScheduledJob(
            Long boardId,
            LocalDateTime scheduledTime
    ) {
        return ScheduledBoardJob.builder()
                .boardId(boardId)
                .scheduledTime(scheduledTime)
                .build();
    }
}
