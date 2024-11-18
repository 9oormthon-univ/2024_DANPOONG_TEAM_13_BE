package com.daon.onjung.onjung.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.onjung.application.dto.response.ReadOnjungSummaryResponseDto;

@UseCase
public interface ReadOnjungSummaryUseCase {

    /**
     * 4.1 전체 온기 통계 조회하기 유스케이스.
     * @return ReadOnjungSummaryResponseDto 전체 온기 통계 조회 응답 DTO
     */
    ReadOnjungSummaryResponseDto execute();
}
