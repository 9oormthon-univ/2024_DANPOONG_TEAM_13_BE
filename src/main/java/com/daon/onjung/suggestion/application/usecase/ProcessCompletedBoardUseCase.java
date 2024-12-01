package com.daon.onjung.suggestion.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;

@UseCase
public interface ProcessCompletedBoardUseCase {

    void execute(Long boardId);
}
