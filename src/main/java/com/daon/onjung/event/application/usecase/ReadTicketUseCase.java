package com.daon.onjung.event.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.event.application.dto.response.ReadTicketResponseDto;

import java.util.UUID;

@UseCase
public interface ReadTicketUseCase {
    /**
     * 추천 매장 리스트 조회하기
     * @param page              페이지
     * @param size              페이지 크기
     * @param accountId         계정 ID
     * @return 식권 목록 조회 응답 DTO
     */

    ReadTicketResponseDto execute(
            Integer page,
            Integer size,
            UUID accountId
    );
}
