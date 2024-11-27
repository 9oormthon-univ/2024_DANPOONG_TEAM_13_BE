package com.daon.onjung.suggestion.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.suggestion.application.dto.request.LikeMessage;
import com.daon.onjung.suggestion.application.dto.response.CreateOrDeleteLikeResponseDto;

@UseCase
public interface SendLikeRequestUseCase {
    CreateOrDeleteLikeResponseDto execute(LikeMessage likeMessage);
}
