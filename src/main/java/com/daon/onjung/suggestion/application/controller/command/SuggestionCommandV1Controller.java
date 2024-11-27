package com.daon.onjung.suggestion.application.controller.command;

import com.daon.onjung.core.annotation.security.AccountID;
import com.daon.onjung.core.dto.ResponseDto;
import com.daon.onjung.suggestion.application.dto.request.CreateBoardRequestDto;
import com.daon.onjung.suggestion.application.dto.request.CreateCommentRequestDto;
import com.daon.onjung.suggestion.application.dto.response.CreateBoardResponseDto;
import com.daon.onjung.suggestion.application.dto.response.CreateCommentResponseDto;
import com.daon.onjung.suggestion.application.dto.response.CreateOrDeleteLikeResponseDto;
import com.daon.onjung.suggestion.application.usecase.CreateBoardUseCase;
import com.daon.onjung.suggestion.application.usecase.CreateCommentUseCase;
import com.daon.onjung.suggestion.application.usecase.CreateOrDeleteLikeUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SuggestionCommandV1Controller {

    private final CreateBoardUseCase createBoardUseCase;
    private final CreateCommentUseCase createCommentUseCase;
    private final CreateOrDeleteLikeUseCase createOrDeleteLikeUseCase;

    @PostMapping(value = "/api/v1/boards", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto<CreateBoardResponseDto> createBoard(
            @AccountID UUID accountId,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("body") @Valid CreateBoardRequestDto requestDto
    ) {
        return ResponseDto.created(createBoardUseCase.execute(accountId, file, requestDto));
    }

    @PostMapping("/api/v1/boards/{id}/comments")
    public ResponseDto<CreateCommentResponseDto> createComment(
            @AccountID UUID accountId,
            @PathVariable Long id,
            @RequestBody @Valid CreateCommentRequestDto requestDto
    ) {
        return ResponseDto.created(createCommentUseCase.execute(accountId, id, requestDto));
    }

    @PutMapping("/api/v1/boards/{id}/likes")
    public ResponseDto<CreateOrDeleteLikeResponseDto> likeBoard(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(createOrDeleteLikeUseCase.execute(accountId, id));
    }
}
