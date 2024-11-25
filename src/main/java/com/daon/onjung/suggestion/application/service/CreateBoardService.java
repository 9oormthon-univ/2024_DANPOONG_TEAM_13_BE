package com.daon.onjung.suggestion.application.service;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.core.utility.S3Util;
import com.daon.onjung.security.domain.type.EImageType;
import com.daon.onjung.suggestion.application.dto.request.CreateBoardRequestDto;
import com.daon.onjung.suggestion.application.usecase.CreateBoardUseCase;
import com.daon.onjung.suggestion.domain.Board;
import com.daon.onjung.suggestion.domain.service.BoardService;
import com.daon.onjung.suggestion.repository.mysql.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateBoardService implements CreateBoardUseCase {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    private final BoardService boardService;

    private final S3Util s3Util;

    @Override
    @Transactional
    public void execute(UUID accountId, MultipartFile file, CreateBoardRequestDto requestDto) {

        // 유저 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 게시글 생성
        Board board = boardService.createBoard(
                requestDto.title(),
                requestDto.content(),
                user
        );
        board = boardRepository.save(board);

        // 파일 업로드
        if (file != null) {
            String imgUrl = s3Util.uploadImageFile(file, board.getId().toString(), EImageType.BOARD_IMG);
            board = boardService.updateBoardFile(board, imgUrl);
            boardRepository.save(board);
        }
    }
}
