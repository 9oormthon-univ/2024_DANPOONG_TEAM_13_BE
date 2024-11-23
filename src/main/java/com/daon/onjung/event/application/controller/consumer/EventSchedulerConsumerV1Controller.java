package com.daon.onjung.event.application.controller.consumer;

import com.daon.onjung.event.repository.redis.ScheduledEventJobRepository;
import com.daon.onjung.event.application.usecase.ProcessCompletedEventUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@RequiredArgsConstructor
@Slf4j
public class EventSchedulerConsumerV1Controller implements Job {

    private final ProcessCompletedEventUseCase processCompletedEventUseCase;
    private final ScheduledEventJobRepository scheduledEventJobRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // JobDataMap을 통해 eventId를 가져온다.
        Long eventId = context.getJobDetail().getJobDataMap().getLong("eventId");

        // eventId를 통해 ScheduledEventJob을 삭제
        scheduledEventJobRepository.findByEventId(eventId)
                .ifPresent(scheduledEventJobRepository::delete);
        log.info("ScheduledEventJob 삭제 완료. eventId: {}", eventId);

        processCompletedEventUseCase.execute(eventId);
    }
}
