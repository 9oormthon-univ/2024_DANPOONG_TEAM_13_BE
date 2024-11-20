package com.daon.onjung.event.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.event.application.dto.response.ReadTicketBriefResponseDto;

@UseCase
public interface ReadTicketBriefUseCase {

    /**
     * 식권 QR 코드 조회하기
     * @param id 티켓 ID
     * @return 식권 QR 코드 조회 응답 DTO
     */
    ReadTicketBriefResponseDto execute(Long id);
}
