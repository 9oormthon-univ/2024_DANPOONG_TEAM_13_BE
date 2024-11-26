package com.daon.onjung.suggestion.domain.service;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.suggestion.domain.Board;
import com.daon.onjung.suggestion.domain.Comment;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    public Comment createComment(
        String content,
        User user,
        Board board
    ) {
        return Comment.builder()
            .content(content)
            .user(user)
            .board(board)
            .build();
    }
}
