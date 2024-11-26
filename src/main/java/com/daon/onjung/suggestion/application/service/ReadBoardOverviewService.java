package com.daon.onjung.suggestion.application.service;

import com.daon.onjung.suggestion.application.dto.response.ReadBoardOverviewResponseDto;
import com.daon.onjung.suggestion.application.usecase.ReadBoardOverviewUseCase;
import com.daon.onjung.suggestion.domain.Board;
import com.daon.onjung.suggestion.repository.mysql.BoardRepository;
import com.daon.onjung.suggestion.repository.mysql.CommentRepository;
import com.daon.onjung.suggestion.repository.mysql.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReadBoardOverviewService implements ReadBoardOverviewUseCase {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadBoardOverviewResponseDto execute(Integer page, Integer size) {

        // 게시글 조회
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Board> boardsPage = boardRepository.findAllByOrderByCreatedAtDesc(pageable);

        List<ReadBoardOverviewResponseDto.BoardListDto> boardList = boardsPage.getContent().stream()
                .map(board -> {

                    // 각 게시글에 대해 댓글 수 조회
                    Integer commentCount = commentRepository.countByBoard(board);

                    // 각 게시글에 대해 좋아요 수 조회
                    Integer likeCount = likeRepository.countByBoard(board);

                    // BoardListDto 생성
                    ReadBoardOverviewResponseDto.BoardListDto boardListDto =
                            ReadBoardOverviewResponseDto.BoardListDto.of(board, commentCount, likeCount);

                    return boardListDto;
                })
                .toList();

        // ResponseDto 생성
        return ReadBoardOverviewResponseDto.of(boardList, boardsPage.hasNext());
    }
}
