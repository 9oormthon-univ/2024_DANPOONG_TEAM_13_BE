package com.daon.onjung.suggestion.application.controller.consumer;

import com.daon.onjung.suggestion.application.usecase.ProcessCompletedBoardUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

@RequiredArgsConstructor
@Slf4j
public class BoardSchedulerConsumerV1Controller implements Job {

    private final ProcessCompletedBoardUseCase processCompletedBoardUseCase;

    @Override
    public void execute(JobExecutionContext context) {
        // JobDataMap을 통해 boardId를 가져온다.
        Long boardId = context.getJobDetail().getJobDataMap().getLong("boardId");

        processCompletedBoardUseCase.execute(boardId);
    }


}
