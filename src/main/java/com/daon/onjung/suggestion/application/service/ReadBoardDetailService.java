package com.daon.onjung.suggestion.application.service;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.suggestion.application.dto.response.ReadBoardDetailResponseDto;
import com.daon.onjung.suggestion.application.usecase.ReadBoardDetailUseCase;
import com.daon.onjung.suggestion.domain.Board;
import com.daon.onjung.suggestion.repository.mysql.BoardRepository;
import com.daon.onjung.suggestion.repository.mysql.CommentRepository;
import com.daon.onjung.suggestion.repository.mysql.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadBoardDetailService implements ReadBoardDetailUseCase {

    private final BoardRepository boardRepository;
    private final LikeRepository likeRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadBoardDetailResponseDto execute(UUID accountId, Long boardId) {

        // 유저 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 게시글 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 좋아요 개수 조회
        Integer likeCount = likeRepository.countByBoard(board);

        // 댓글 개수 조회
        Integer commentCount = commentRepository.countByBoard(board);

        // 좋아요 여부 조회
        Boolean isLiked = likeRepository.existsByBoardAndUser(board, user);

        // 작성자 조회
        User writer = board.getUser();

        return ReadBoardDetailResponseDto.of(
                board,
                writer,
                likeCount,
                commentCount,
                isLiked
        );
    }
}
