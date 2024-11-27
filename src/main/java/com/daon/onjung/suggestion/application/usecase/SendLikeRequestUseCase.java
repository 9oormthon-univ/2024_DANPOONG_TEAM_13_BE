package com.daon.onjung.suggestion.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.suggestion.application.dto.request.LikeMessage;

@UseCase
public interface SendLikeRequestUseCase {
    void execute(LikeMessage likeMessage);
}
