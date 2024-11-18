package com.daon.onjung.security.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.security.application.dto.request.SignUpOwnerByDefaultRequestDto;
import org.springframework.web.multipart.MultipartFile;

@UseCase
public interface SignUpOwnerByDefaultUseCase {
    void execute(MultipartFile logo,
                 MultipartFile banner,
                 SignUpOwnerByDefaultRequestDto requestDto
    );
}
