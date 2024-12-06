package com.daon.onjung.suggestion.application.service;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.suggestion.application.dto.response.ReadCommentOverviewResponseDto;
import com.daon.onjung.suggestion.application.usecase.ReadCommentOverviewUseCase;
import com.daon.onjung.suggestion.domain.mysql.Comment;
import com.daon.onjung.suggestion.repository.mysql.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadCommentOverviewService implements ReadCommentOverviewUseCase {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadCommentOverviewResponseDto execute(UUID accountId, Long boardId, Integer page, Integer size) {

        // 댓글 조회
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<Comment> comments = commentRepository.findAllByBoardIdOrderByCreatedAtAsc(boardId, pageable);

        // 유저 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        List<ReadCommentOverviewResponseDto.CommentDto> commentList = comments.getContent().stream()
                .map(comment -> {
                    Boolean isMe = comment.getUser().getId().equals(user.getId());
                    return ReadCommentOverviewResponseDto.CommentDto.of(comment, isMe);
                })
                .toList();

        return ReadCommentOverviewResponseDto.of(commentList, comments.hasNext());
    }
}
