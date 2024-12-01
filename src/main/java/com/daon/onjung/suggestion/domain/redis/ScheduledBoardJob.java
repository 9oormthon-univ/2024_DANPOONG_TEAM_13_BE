package com.daon.onjung.suggestion.domain.redis;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "ScheduledJob", timeToLive = 60 * 60 * 24 * 30) // 30일
public class ScheduledBoardJob {
    @Id
    private String jobId;

    @Indexed
    private Long boardId;

    private LocalDateTime scheduledTime;

    @Builder
    public ScheduledBoardJob(Long boardId, LocalDateTime scheduledTime) {
        this.jobId = boardId.toString();
        this.boardId = boardId;
        this.scheduledTime = scheduledTime;
    }
}
