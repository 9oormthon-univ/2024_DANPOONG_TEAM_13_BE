package com.daon.onjung.suggestion.application.service;

import com.daon.onjung.suggestion.application.dto.response.ReadBoardOverviewResponseDto;
import com.daon.onjung.suggestion.application.usecase.ReadBoardOverviewUseCase;
import com.daon.onjung.suggestion.domain.mysql.Board;
import com.daon.onjung.suggestion.repository.mysql.BoardRepository;
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

    @Override
    @Transactional(readOnly = true)
    public ReadBoardOverviewResponseDto execute(Integer page, Integer size) {

        // 게시글 조회
        Pageable pageable = PageRequest.of(page-1, size);
        Page<Board> boardsPage = boardRepository.findAllByOrderByCreatedAtDesc(pageable);

        // BoardListDto 생성
        List<ReadBoardOverviewResponseDto.BoardListDto> boardList = boardsPage.getContent().stream()
                .map(ReadBoardOverviewResponseDto.BoardListDto::fromEntity)
                .toList();

        // ResponseDto 생성
        return ReadBoardOverviewResponseDto.of(boardList, boardsPage.hasNext());
    }
}
