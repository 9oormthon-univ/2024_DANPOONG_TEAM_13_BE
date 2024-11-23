package com.daon.onjung.event.domain.service;

import com.daon.onjung.event.domain.redis.ScheduledEventJob;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ScheduledEventJobService {

    public ScheduledEventJob createScheduledJob(
            Long eventId,
            LocalDateTime scheduledTime
    ) {
        return ScheduledEventJob.builder()
                .eventId(eventId)
                .scheduledTime(scheduledTime)
                .build();
    }
}
