package com.daon.onjung.suggestion.application.controller.consumer;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.suggestion.application.dto.request.LikeMessage;
import com.daon.onjung.suggestion.domain.Board;
import com.daon.onjung.suggestion.domain.Like;
import com.daon.onjung.suggestion.domain.service.BoardService;
import com.daon.onjung.suggestion.domain.service.LikeService;
import com.daon.onjung.suggestion.repository.mysql.BoardRepository;
import com.daon.onjung.suggestion.repository.mysql.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.lock.OptimisticEntityLockException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeV1Consumer {

    private final LikeRepository likeRepository;
    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    private final LikeService likeService;
    private final BoardService boardService;

    @Transactional
    @RabbitListener(queues = "like-queue")
    public void processLikeMessage(LikeMessage likeMessage) {
        try {

            // 게시글 조회
            Board board = boardRepository.findById(likeMessage.boardId())
                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

            // 유저 조회
            User user = userRepository.findById(likeMessage.userId())
                    .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

            // 좋아요가 존재하는지 확인
            Like like = likeRepository.findByBoardAndUser(board, user)
                    .orElse(null);

            if (like != null) {

                // 좋아요가 이미 존재하면 삭제
                likeRepository.delete(like);
                // 게시글 좋아요 수 감소
                board = boardService.subtractLikeCount(board);
                boardRepository.save(board);
            } else {

                // 좋아요가 존재하지 않으면 생성
                likeRepository.save(likeService.createLike(user, board));

                // 게시글 좋아요 수 증가
                board = boardService.addLikeCount(board);
                boardRepository.save(board);
            }
        } catch (OptimisticEntityLockException e) {
            throw new CommonException(ErrorCode.OPTIMISTIC_EXCEPTION);
        }
    }

}
