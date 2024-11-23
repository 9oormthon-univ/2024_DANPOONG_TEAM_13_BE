package com.daon.onjung.core.listener;

import com.daon.onjung.event.domain.redis.ScheduledEventJob;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.event.repository.redis.ScheduledEventJobRepository;
import com.daon.onjung.event.application.controller.consumer.EventSchedulerConsumerV1Controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
@RequiredArgsConstructor
@Slf4j
public class AppEventListener {

    private final Scheduler scheduler;

    private final ScheduledEventJobRepository scheduledEventJobRepository;

    @EventListener
    public void handleEventScheduled(ScheduledEventJob scheduledEventJob) {
        JobDetail jobDetail = JobBuilder.newJob(EventSchedulerConsumerV1Controller.class)
                .withIdentity("eventJob-" + scheduledEventJob.getJobId(), "eventGroup")
                .usingJobData("eventId", scheduledEventJob.getEventId())
                .build();
        log.info("Job 등록 완료. eventId: {}", scheduledEventJob.getEventId());

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("eventTrigger-" + scheduledEventJob.getJobId(), "eventGroup")
                .startAt(Timestamp.valueOf(scheduledEventJob.getScheduledTime()))
                .build();
        log.info("eventId {}에 대한 Trigger 등록 완료.", scheduledEventJob.getEventId());
        log.info("Trigger 시작 시간: {}", trigger.getStartTime());

        try {
            scheduler.scheduleJob(jobDetail, trigger);
            scheduledEventJobRepository.save(scheduledEventJob);
        } catch (SchedulerException e) {
            throw new CommonException(ErrorCode.SCHEDULER_ERROR);
        }
    }

}
