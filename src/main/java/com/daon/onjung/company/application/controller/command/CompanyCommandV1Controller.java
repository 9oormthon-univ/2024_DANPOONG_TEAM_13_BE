package com.daon.onjung.company.application.controller.command;

import com.daon.onjung.company.application.dto.request.CreateCompanyRequestDto;
import com.daon.onjung.company.application.usecase.CreateCompanyUseCase;
import com.daon.onjung.core.dto.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class CompanyCommandV1Controller {

    private final CreateCompanyUseCase createCompanyUseCase;

    /**
     * 6.2 회사 등록 (데이터 삽입용)
     */
    @PostMapping(value = "/api/v1/company",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_JSON_VALUE})
    public ResponseDto<Void> createCompany(
            @RequestPart(value = "company_image", required = false) MultipartFile companyImage,
            @Valid @RequestPart(value = "body")CreateCompanyRequestDto requestDto
    ) {
        createCompanyUseCase.execute(companyImage, requestDto);
        return ResponseDto.created(null);
    }
}
