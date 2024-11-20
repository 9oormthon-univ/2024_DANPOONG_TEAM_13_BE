package com.daon.onjung.company.application.controller.query;

import com.daon.onjung.company.application.dto.response.ReadCompanyBriefResponseDto;
import com.daon.onjung.company.application.usecase.ReadCompanyBriefUseCase;
import com.daon.onjung.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class CompanyQueryV1Controller {
    private final ReadCompanyBriefUseCase readCompanyBriefUseCase;

    /**
     * 6.1 온기를 전한 기업들 조회
     */
    @GetMapping("/company/briefs")
    public ResponseDto<ReadCompanyBriefResponseDto> readCompanyBrief() {
        return ResponseDto.ok(
                readCompanyBriefUseCase.execute()
        );
    }
}
