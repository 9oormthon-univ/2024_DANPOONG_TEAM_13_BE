package com.daon.onjung.event.application.controller.consumer;

import com.daon.onjung.event.application.usecase.ProcessCompletedEventUseCase;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

@RequiredArgsConstructor
public class EventSchedulerConsumerV1Controller implements Job {

    private final ProcessCompletedEventUseCase processCompletedEventUseCase;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Long eventId = context.getJobDetail().getJobDataMap().getLong("eventId");

        processCompletedEventUseCase.execute(eventId);
    }
}
