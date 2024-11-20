package com.daon.onjung.account.application.usecase;

import com.daon.onjung.account.application.dto.response.ReadStoreDetailResponseDto;
import com.daon.onjung.core.annotation.bean.UseCase;

@UseCase
public interface ReadStoreDetailUseCase {
    /**
     * 상점 상세 정보 조회하기
     * @param id 상점 ID
     * @return 상점 상세 정보 조회 응답 DTO
     */

    ReadStoreDetailResponseDto execute(Long id);
}
