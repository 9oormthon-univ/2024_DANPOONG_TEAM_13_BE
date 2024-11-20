package com.daon.onjung.event.domain.event;

import lombok.Builder;

import java.time.LocalDateTime;

public record EventScheduled(
        Long eventId,
        LocalDateTime scheduledTime
) {
    @Builder
    public EventScheduled(Long eventId, LocalDateTime scheduledTime) {
        this.eventId = eventId;
        this.scheduledTime = scheduledTime;
    }
}
