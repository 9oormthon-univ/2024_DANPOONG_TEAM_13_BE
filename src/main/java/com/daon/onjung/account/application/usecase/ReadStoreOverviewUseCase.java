package com.daon.onjung.account.application.usecase;

import com.daon.onjung.account.application.dto.response.ReadStoreOverviewsResponseDto;
import com.daon.onjung.core.annotation.bean.UseCase;

@UseCase
public interface ReadStoreOverviewUseCase {
    /**
     * 추천 매장 리스트 조회하기
     * @param page              페이지
     * @param size              페이지 크기
     * @param title             공고 제목
     * @param onjungTags        포함 태그
     * @param sortByOnjungCount   기부 횟수 정렬 (asc, desc)
     *
     * @return 상점 목록 조회 응답 DTO
     */

    ReadStoreOverviewsResponseDto execute(
            Integer page,
            Integer size,
            String title,
            String onjungTags,
            String sortByOnjungCount
    );
}
