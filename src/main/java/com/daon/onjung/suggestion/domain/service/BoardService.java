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
}
