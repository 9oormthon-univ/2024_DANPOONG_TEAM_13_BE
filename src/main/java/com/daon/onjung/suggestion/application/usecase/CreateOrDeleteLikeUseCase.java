package com.daon.onjung.suggestion.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.suggestion.application.dto.response.CreateOrDeleteLikeResponseDto;

import java.util.UUID;

@UseCase
public interface CreateOrDeleteLikeUseCase {

    CreateOrDeleteLikeResponseDto execute(UUID accountId, Long boardId);
}
