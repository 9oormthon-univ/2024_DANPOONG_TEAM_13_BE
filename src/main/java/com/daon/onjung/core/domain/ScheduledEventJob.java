package com.daon.onjung.core.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "ScheduledJob", timeToLive = 60 * 60 * 24 * 14) // 14일
//@RedisHash(value = "ScheduledJob", timeToLive = 60 * 5) // 테스트용 5분
public class ScheduledEventJob {
    @Id
    private String jobId;

    @Indexed
    private Long eventId;

    private LocalDateTime scheduledTime;

    @Builder
    public ScheduledEventJob(Long eventId, LocalDateTime scheduledTime) {
        this.jobId = UUID.randomUUID().toString();
        this.eventId = eventId;
        this.scheduledTime = scheduledTime;
    }
}