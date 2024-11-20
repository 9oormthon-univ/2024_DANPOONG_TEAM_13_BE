package com.daon.onjung.company.application.usecase;

import com.daon.onjung.company.application.dto.request.CreateCompanyRequestDto;
import com.daon.onjung.core.annotation.bean.UseCase;

@UseCase
public interface CreateCompanyUseCase {

    Void execute(CreateCompanyRequestDto requestDto);
}
