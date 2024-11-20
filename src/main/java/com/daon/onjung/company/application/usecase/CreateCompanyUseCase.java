package com.daon.onjung.company.application.usecase;

import com.daon.onjung.company.application.dto.request.CreateCompanyRequestDto;
import com.daon.onjung.core.annotation.bean.UseCase;
import org.springframework.web.multipart.MultipartFile;

@UseCase
public interface CreateCompanyUseCase {
    void execute(
            MultipartFile companyImage,
            CreateCompanyRequestDto requestDto
    );
}
