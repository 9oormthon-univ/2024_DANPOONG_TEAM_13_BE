package com.daon.onjung.suggestion.application.service;

import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.suggestion.application.usecase.ProcessCompletedBoardUseCase;
import com.daon.onjung.suggestion.domain.mysql.Board;
import com.daon.onjung.suggestion.domain.service.BoardService;
import com.daon.onjung.suggestion.repository.mysql.BoardRepository;
import com.daon.onjung.suggestion.repository.redis.ScheduledBoardJobRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessCompletedBoardService implements ProcessCompletedBoardUseCase {

    private final ScheduledBoardJobRepository scheduledBoardJobRepository;
    private final BoardRepository boardRepository;

    private final BoardService boardService;

    @Override
    @Transactional
    public void execute(Long boardId) {

        // boardId를 통해 ScheduledBoardJob을 삭제
        scheduledBoardJobRepository.findByBoardId(boardId)
                .ifPresent(scheduledBoardJobRepository::delete);
        log.info("ScheduledBoardJob 삭제 완료. boardId: {}", boardId);


        // 게시글 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 게시글 기간 완료 처리
        board = boardService.processCompletedBoard(board);
        boardRepository.save(board);

        log.info("게시글 완료 처리 완료. boardId: {}", boardId);
    }
}
