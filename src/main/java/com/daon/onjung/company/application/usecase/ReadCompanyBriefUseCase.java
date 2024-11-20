package com.daon.onjung.company.application.usecase;

import com.daon.onjung.company.application.dto.response.ReadCompanyBriefResponseDto;
import com.daon.onjung.core.annotation.bean.UseCase;

@UseCase
public interface ReadCompanyBriefUseCase {

    ReadCompanyBriefResponseDto execute();
}
