package com.daon.onjung.account.application.usecase;

import com.daon.onjung.account.application.dto.response.ReadStoreOverviewsResponseDto;
import com.daon.onjung.account.domain.type.EOnjungTag;
import com.daon.onjung.core.annotation.bean.UseCase;

import java.util.List;

@UseCase
public interface ReadStoreOverviewUseCase {
    /**
     * 추천 매장 리스트 조회하기
     * @param page              페이지
     * @param size              페이지 크기
     * @param title             공고 제목
     * @param tags              포함 태그
     * @param sortByDonationCount   기부 횟수 정렬 (asc, desc)
     *
     * @return 상점 목록 조회 응답 DTO
     */

    ReadStoreOverviewsResponseDto execute(
            Integer page,
            Integer size,
            String title,
            List<EOnjungTag> tags,
            String sortByDonationCount
    );
}
