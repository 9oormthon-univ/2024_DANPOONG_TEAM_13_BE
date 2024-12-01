package com.daon.onjung.suggestion.application.controller.consumer;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.suggestion.application.dto.request.CommentMessage;
import com.daon.onjung.suggestion.application.dto.response.CreateCommentResponseDto;
import com.daon.onjung.suggestion.domain.mysql.Board;
import com.daon.onjung.suggestion.domain.mysql.Comment;
import com.daon.onjung.suggestion.domain.service.BoardService;
import com.daon.onjung.suggestion.domain.service.CommentService;
import com.daon.onjung.suggestion.repository.mysql.BoardRepository;
import com.daon.onjung.suggestion.repository.mysql.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentConsumerV1Controller {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    private final CommentService commentService;
    private final BoardService boardService;

    private final RabbitTemplate rabbitTemplate;

    @Transactional
    @RabbitListener(queues = "comment-queue-1")
    public CreateCommentResponseDto processCommentMessage1(CommentMessage commentMessage) {
        try {

            // 게시글 조회
            Board board = boardRepository.findById(commentMessage.boardId())
                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

            // 유저 조회
            User user = userRepository.findById(commentMessage.userId())
                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

            // 댓글 생성
            Comment comment = commentService.createComment(
                    commentMessage.content(),
                    user,
                    board
            );
            commentRepository.save(comment);

            // 게시글 댓글 수 증가
            board = boardService.addCommentCount(board);
            boardRepository.save(board);

            return CreateCommentResponseDto.of(user, true, comment);
        } catch (OptimisticEntityLockException e) {
            throw new CommonException(ErrorCode.OPTIMISTIC_EXCEPTION);
        }
    }

    @Transactional
    @RabbitListener(queues = "comment-queue-2")
    public CreateCommentResponseDto processCommentMessage2(CommentMessage commentMessage) {
        try {

            // 게시글 조회
            Board board = boardRepository.findById(commentMessage.boardId())
                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

            // 유저 조회
            User user = userRepository.findById(commentMessage.userId())
                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

            // 댓글 생성
            Comment comment = commentService.createComment(
                    commentMessage.content(),
                    user,
                    board
            );
            commentRepository.save(comment);

            // 게시글 댓글 수 증가
            board = boardService.addCommentCount(board);
            boardRepository.save(board);

            return CreateCommentResponseDto.of(user, true, comment);
        } catch (OptimisticEntityLockException e) {
            throw new CommonException(ErrorCode.OPTIMISTIC_EXCEPTION);
        }
    }


}
