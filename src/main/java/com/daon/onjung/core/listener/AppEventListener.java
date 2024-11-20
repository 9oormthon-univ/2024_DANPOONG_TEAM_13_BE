package com.daon.onjung.core.listener;

import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.event.application.controller.consumer.EventSchedulerConsumerV1Controller;
import com.daon.onjung.event.domain.event.EventScheduled;
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

    @EventListener
    public void handleEventScheduled(EventScheduled eventScheduled) {
        JobDetail jobDetail = JobBuilder.newJob(EventSchedulerConsumerV1Controller.class)
                .withIdentity("eventJob-" + eventScheduled.eventId(), "eventGroup")
                .usingJobData("eventId", eventScheduled.eventId())
                .build();
        log.info("Job 등록 완료. JobKey: {}", jobDetail.getKey());

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("eventTrigger-" + eventScheduled.eventId(), "eventGroup")
                .startAt(Timestamp.valueOf(eventScheduled.scheduledTime()))
                .build();
        log.info("Trigger 등록 완료. TriggerKey: {}", trigger.getKey());
        log.info("Trigger 시작 시간: {}", trigger.getStartTime());

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            throw new CommonException(ErrorCode.SCHEDULER_ERROR);
        }
    }

}
