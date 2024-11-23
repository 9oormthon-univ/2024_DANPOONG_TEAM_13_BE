package com.daon.onjung.core.domain.service;

import com.daon.onjung.core.domain.ScheduledEventJob;
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
