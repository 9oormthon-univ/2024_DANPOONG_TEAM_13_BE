package com.daon.onjung.core.listener;

import com.daon.onjung.core.domain.ScheduledEventJob;
import com.daon.onjung.core.repository.redis.ScheduledEventJobRepository;
import com.daon.onjung.event.application.controller.consumer.EventSchedulerConsumerV1Controller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SchedulerRecoveryListener implements CommandLineRunner {

    private final ScheduledEventJobRepository scheduledEventJobRepository;
    private final Scheduler scheduler;

    @Override
    public void run(String... args) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        List<ScheduledEventJob> pendingJobs = scheduledEventJobRepository.findAll();

        for (ScheduledEventJob job : pendingJobs) {
            if (job.getScheduledTime().isAfter(now)) {
                log.info("미래 작업 스케줄러에 재등록. eventId: {}", job.getEventId());
                scheduleJob(job);
            } else {
                executeJobImmediately(job);
            }
        }
    }

    private void scheduleJob(ScheduledEventJob job) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(EventSchedulerConsumerV1Controller.class)
                .withIdentity("eventJob-" + job.getEventId(), "eventGroup")
                .usingJobData("eventId", job.getEventId())
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger-" + job.getJobId(), "eventGroup")
                .startAt(Timestamp.valueOf(job.getScheduledTime()))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    private void executeJobImmediately(ScheduledEventJob scheduledEventJob) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(EventSchedulerConsumerV1Controller.class)
                    .withIdentity("eventJob-" + scheduledEventJob.getJobId(), "eventGroup")
                    .usingJobData("eventId", scheduledEventJob.getEventId())
                    .build();
            log.info("Job 등록 완료. eventId: {}", scheduledEventJob.getEventId());

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("eventTrigger-" + scheduledEventJob.getJobId(), "eventGroup")
                    .startNow()
                    .build();

            // 작업 스케줄링
            scheduler.scheduleJob(jobDetail, trigger);

            // 실행 성공 시 작업 삭제
            scheduledEventJobRepository.delete(scheduledEventJob);

            log.info("이전에 등록했지만 실행안된, 기간이 지난 Job을 즉시 실행함. eventId: {}", scheduledEventJob.getEventId());
        } catch (SchedulerException e) {
            log.error("Job 즉시실행에 실패. eventId: {}", scheduledEventJob.getEventId(), e);
            // 실패 시 삭제하지 않음. 필요하면 재시도 큐에 추가하는 로직 고려
        }
    }
}
