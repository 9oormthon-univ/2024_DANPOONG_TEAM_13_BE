package com.daon.onjung.event.application.service;

import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.core.utility.AESUtil;
import com.daon.onjung.event.application.dto.request.ReadTicketValidateRequestDto;
import com.daon.onjung.event.application.dto.response.ReadTicketValidateResponseDto;
import com.daon.onjung.event.application.usecase.ReadTicketValidateUseCase;
import com.daon.onjung.event.domain.Ticket;
import com.daon.onjung.event.domain.service.TicketService;
import com.daon.onjung.event.repository.mysql.TicketRepository;
import com.daon.onjung.security.repository.mysql.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReadTicketValidateService implements ReadTicketValidateUseCase {

    private final TicketRepository ticketRepository;
    private final AccountRepository accountRepository;

    private final TicketService ticketService;

    private final AESUtil aesUtil;

    @Override
    @Transactional(readOnly = true)
    public ReadTicketValidateResponseDto execute(ReadTicketValidateRequestDto requestDto) {

        String ticketId = aesUtil.decrypt_AES(requestDto.hashedTicketId());

        // 티켓 조회
        Ticket ticket = ticketRepository.findById(Long.valueOf(ticketId))
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 티켓 만료일 확인
        if (ticket.getExpirationDate().isBefore(LocalDate.now())) {
            return ReadTicketValidateResponseDto.fromEntity(false, "사용 기한이 만료된 티켓입니다.");
        }

        // uuid로 account의 password 조회
        String password = accountRepository.findPasswordById(ticket.getStore().getOwner().getId())
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 비밀번호 확인
        if (!password.equals(requestDto.password())) {
            return ReadTicketValidateResponseDto.fromEntity(false, "비밀번호가 일치하지 않습니다.");
        }

        ticketService.updateIsValidate(ticket);

        return ReadTicketValidateResponseDto.fromEntity(true, "성공입니다.");
    }
}
