package com.daon.onjung.suggestion.application.controller.command;

import com.daon.onjung.core.annotation.security.AccountID;
import com.daon.onjung.core.dto.ResponseDto;
import com.daon.onjung.suggestion.application.dto.request.CreateBoardRequestDto;
import com.daon.onjung.suggestion.application.dto.request.CreateCommentRequestDto;
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
    public ResponseDto<Void> createBoard(
            @AccountID UUID accountId,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @RequestPart("body") @Valid CreateBoardRequestDto requestDto
    ) {
        createBoardUseCase.execute(accountId, file, requestDto);
        return ResponseDto.created(null);
    }

    @PostMapping("/api/v1/boards/{id}/comments")
    public ResponseDto<Void> createComment(
            @AccountID UUID accountId,
            @PathVariable Long id,
            @RequestBody @Valid CreateCommentRequestDto requestDto
    ) {
        createCommentUseCase.execute(accountId, id, requestDto);
        return ResponseDto.created(null);
    }

    @PutMapping("/api/v1/boards/{id}/likes")
    public ResponseDto<Void> likeBoard(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        if (createOrDeleteLikeUseCase.execute(accountId, id)) {
            return ResponseDto.created(null);
        } else {
            return ResponseDto.ok(null);
        }
    }
}
