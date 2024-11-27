package com.daon.onjung.suggestion.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;

import java.util.UUID;

@UseCase
public interface CreateOrDeleteLikeUseCase {

    void execute(UUID accountId, Long boardId);
}
