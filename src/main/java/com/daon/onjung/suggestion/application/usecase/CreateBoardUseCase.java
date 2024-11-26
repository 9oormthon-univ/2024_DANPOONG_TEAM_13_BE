package com.daon.onjung.suggestion.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.suggestion.application.dto.request.CreateBoardRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@UseCase
public interface CreateBoardUseCase {

    void execute(UUID accountId, MultipartFile file, CreateBoardRequestDto requestDto);
}
