package com.daon.onjung.company.application.service;

import com.daon.onjung.company.application.dto.request.CreateCompanyRequestDto;
import com.daon.onjung.company.application.usecase.CreateCompanyUseCase;
import com.daon.onjung.company.domain.Company;
import com.daon.onjung.company.domain.service.CompanyService;
import com.daon.onjung.company.repository.mysql.CompanyRepository;
import com.daon.onjung.core.utility.S3Util;
import com.daon.onjung.security.domain.type.EImageType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class CreateCompanyService implements CreateCompanyUseCase {

    private final CompanyRepository companyRepository;

    private final CompanyService companyService;

    private final S3Util s3Util;

    public void execute(
            MultipartFile companyImage,
            CreateCompanyRequestDto requestDto
    ) {

        // 로고 이미지 저장
        String company_image_url = s3Util.uploadImageFile(companyImage, requestDto.name(), EImageType.LOGO_IMG);

        Company company = companyService.createCompany(
                requestDto.name(),
                company_image_url
        );

        companyRepository.save(company);
    }
}
