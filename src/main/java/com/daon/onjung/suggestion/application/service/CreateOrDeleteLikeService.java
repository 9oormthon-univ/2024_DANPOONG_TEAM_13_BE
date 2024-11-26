package com.daon.onjung.suggestion.application.service;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.suggestion.application.usecase.CreateOrDeleteLikeUseCase;
import com.daon.onjung.suggestion.domain.Board;
import com.daon.onjung.suggestion.domain.Like;
import com.daon.onjung.suggestion.domain.service.LikeService;
import com.daon.onjung.suggestion.repository.mysql.BoardRepository;
import com.daon.onjung.suggestion.repository.mysql.LikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateOrDeleteLikeService implements CreateOrDeleteLikeUseCase {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final LikeService likeService;

    @Override
    @Transactional
    public Boolean execute(UUID accountId, Long boardId) {

        // 유저 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 게시글 조회
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 좋아요 여부 확인
        Like like = likeRepository.findByBoardAndUser(board, user)
                .orElse(null);

        // 좋아요 생성 또는 삭제
        if (like != null) {
            likeRepository.delete(like);
            return false;
        } else {
            likeRepository.save(likeService.createLike(user,board));
            return true;
        }
    }
}
