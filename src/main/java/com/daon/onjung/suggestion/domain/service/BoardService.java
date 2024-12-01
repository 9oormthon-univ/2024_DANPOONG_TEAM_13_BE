package com.daon.onjung.suggestion.domain.service;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.suggestion.domain.mysql.Board;
import com.daon.onjung.suggestion.domain.type.EStatus;
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

    public Board processCompletedBoard(Board board) {
        Integer likeCount = board.getLikeCount();

        if (likeCount >= 100) {
            board.updateStatus(EStatus.UNDER_REVIEW);
        } else {
            board.updateStatus(EStatus.EXPIRED);
        }

        return board;
    }
}
