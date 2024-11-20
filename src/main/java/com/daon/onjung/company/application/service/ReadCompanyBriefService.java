package com.daon.onjung.company.application.service;

import com.daon.onjung.company.application.dto.response.ReadCompanyBriefResponseDto;
import com.daon.onjung.company.application.usecase.ReadCompanyBriefUseCase;
import com.daon.onjung.company.repository.mysql.CompanyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadCompanyBriefService implements ReadCompanyBriefUseCase {
    private final CompanyRepository companyRepository;

    // company images만 반환하는 메서드
    public ReadCompanyBriefResponseDto execute() {
        return ReadCompanyBriefResponseDto.fromEntity(
                companyRepository.findAllImgUrls()
        );
    }
}
