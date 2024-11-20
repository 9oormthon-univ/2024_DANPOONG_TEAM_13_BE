package com.daon.onjung.company.application.controller.command;

import com.daon.onjung.company.application.dto.request.CreateCompanyRequestDto;
import com.daon.onjung.company.application.usecase.CreateCompanyUseCase;
import com.daon.onjung.company.repository.mysql.CompanyRepository;
import com.daon.onjung.core.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CompanyCommandV1Controller {

    private final CompanyRepository companyRepository;
    private final CreateCompanyUseCase createCompanyUseCase;

    public ResponseDto<Void> createCompany(
            @RequestBody @Valid CreateCompanyRequestDto requestDto
    ) {
        return ResponseDto.ok(
                createCompanyUseCase.execute(requestDto)
        );
    }
}
