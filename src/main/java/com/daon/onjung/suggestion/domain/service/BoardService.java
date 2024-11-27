package com.daon.onjung.suggestion.domain.service;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.suggestion.domain.Board;
import org.springframework.stereotype.Service;

@Service
public class BoardService {

    public Board createBoard(
            String title,
            String content,
            User user
    ) {
        return Board.builder()
                .title(title)
                .content(content)
                .user(user)
                .build();
    }

    public Board updateBoardFile(Board board, String imgUrl) {
        board.updateImgUrl(imgUrl);
        return board;
    }

    public Board addLikeCount(Board board) {
        board.updateLikeCount(board.getLikeCount() + 1);
        return board;
    }

    public Board subtractLikeCount(Board board) {
        board.updateLikeCount(board.getLikeCount() - 1);
        return board;
    }

    public Board addCommentCount(Board board) {
        board.updateCommentCount(board.getCommentCount() + 1);
        return board;
    }

    public Board subtractCommentCount(Board board) {
        board.updateCommentCount(board.getCommentCount() - 1);
        return board;
    }
}
