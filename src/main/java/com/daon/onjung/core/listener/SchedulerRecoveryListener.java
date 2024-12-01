package com.daon.onjung.core.listener;

import com.daon.onjung.event.domain.redis.ScheduledEventJob;
import com.daon.onjung.event.repository.redis.ScheduledEventJobRepository;
import com.daon.onjung.event.application.controller.consumer.EventSchedulerConsumerV1Controller;
import com.daon.onjung.suggestion.application.controller.consumer.BoardSchedulerConsumerV1Controller;
import com.daon.onjung.suggestion.domain.redis.ScheduledBoardJob;
import com.daon.onjung.suggestion.repository.redis.ScheduledBoardJobRepository;
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
    private final ScheduledBoardJobRepository scheduledBoardJobRepository;

    @Override
    public void run(String... args) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        List<ScheduledEventJob> pendingEventJobs = scheduledEventJobRepository.findAll();
        log.info("미처리 Event Job 조회 완료. 조회된 Job 수: {}", pendingEventJobs.size());
        if (!pendingEventJobs.isEmpty()) {
            for (ScheduledEventJob job : pendingEventJobs) {
                if (job == null) {
                    log.warn("조회된 Job 중 null 객체가 존재합니다.");
                    continue;
                }
                if (job.getScheduledTime() == null) {
                    log.warn("Job의 scheduledTime 값이 null입니다. eventId: {}", job.getEventId());
                    continue;
                }
                if (job.getScheduledTime().isAfter(now)) {
                    log.info("미래 작업 스케줄러에 재등록. eventId: {}", job.getEventId());
                    scheduleEventJob(job);
                } else {
                    executeEventJobImmediately(job);
                }
            }
        }

        List<ScheduledBoardJob> pendingBoardJobs = scheduledBoardJobRepository.findAll();
        log.info("미처리 Board Job 조회 완료. 조회된 Job 수: {}", pendingBoardJobs.size());

        if (pendingBoardJobs.isEmpty()) {
            return;
        }
        for (ScheduledBoardJob job : pendingBoardJobs) {
            if (job == null) {
                log.warn("조회된 Job 중 null 객체가 존재합니다.");
                continue;
            }
            if (job.getScheduledTime() == null) {
                log.warn("Job의 scheduledTime 값이 null입니다. boardId: {}", job.getBoardId());
                continue;
            }
            if (job.getScheduledTime().isAfter(now)) {
                log.info("미래 작업 스케줄러에 재등록. boardId: {}", job.getBoardId());
                scheduleBoardJob(job);
            } else {
                executeBoardJobImmediately(job);
            }
        }
    }

    private void scheduleEventJob(ScheduledEventJob job) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(EventSchedulerConsumerV1Controller.class)
                .withIdentity("eventJob-" + job.getJobId(), "eventGroup")
                .usingJobData("eventId", job.getEventId())
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger-" + job.getJobId(), "eventGroup")
                .startAt(Timestamp.valueOf(job.getScheduledTime()))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    private void executeEventJobImmediately(ScheduledEventJob scheduledEventJob) {
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

    private void scheduleBoardJob(ScheduledBoardJob job) throws SchedulerException {
        JobDetail jobDetail = JobBuilder.newJob(BoardSchedulerConsumerV1Controller.class)
                .withIdentity("boardJob-" + job.getJobId(), "boardGroup")
                .usingJobData("boardId", job.getBoardId())
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger-" + job.getJobId(), "boardGroup")
                .startAt(Timestamp.valueOf(job.getScheduledTime()))
                .build();

        scheduler.scheduleJob(jobDetail, trigger);
    }

    private void executeBoardJobImmediately(ScheduledBoardJob scheduledBoardJob) {
        try {
            JobDetail jobDetail = JobBuilder.newJob(BoardSchedulerConsumerV1Controller.class)
                    .withIdentity("boardJob-" + scheduledBoardJob.getJobId(), "boardGroup")
                    .usingJobData("boardId", scheduledBoardJob.getBoardId())
                    .build();
            log.info("Job 등록 완료. boardId: {}", scheduledBoardJob.getBoardId());

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("boardTrigger-" + scheduledBoardJob.getJobId(), "boardGroup")
                    .startNow()
                    .build();

            // 작업 스케줄링
            scheduler.scheduleJob(jobDetail, trigger);

            // 실행 성공 시 작업 삭제
            scheduledBoardJobRepository.delete(scheduledBoardJob);

            log.info("이전에 등록했지만 실행안된, 기간이 지난 Job을 즉시 실행함. boardId: {}", scheduledBoardJob.getBoardId());
        } catch (SchedulerException e) {
            log.error("Job 즉시실행에 실패. boardId: {}", scheduledBoardJob.getBoardId(), e);
            // 실패 시 삭제하지 않음. 필요하면 재시도 큐에 추가하는 로직 고려
        }
    }
}
