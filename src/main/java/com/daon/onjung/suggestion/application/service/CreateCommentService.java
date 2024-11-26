package com.daon.onjung.suggestion.application.service;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.suggestion.application.dto.request.CreateCommentRequestDto;
import com.daon.onjung.suggestion.application.usecase.CreateCommentUseCase;
import com.daon.onjung.suggestion.domain.Board;
import com.daon.onjung.suggestion.domain.Comment;
import com.daon.onjung.suggestion.domain.service.CommentService;
import com.daon.onjung.suggestion.repository.mysql.BoardRepository;
import com.daon.onjung.suggestion.repository.mysql.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateCommentService implements CreateCommentUseCase {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    private final CommentService commentService;

    @Override
    @Transactional
    public void execute(UUID accountId, Long boardId, CreateCommentRequestDto requestDto) {

        // 유저 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 게시글 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 댓글 생성
        Comment comment = commentService.createComment(
                requestDto.content(),
                user,
                board
        );
        commentRepository.save(comment);
    }
}
