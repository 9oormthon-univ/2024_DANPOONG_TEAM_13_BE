package com.daon.onjung.event.application.service;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.event.application.dto.response.ReadTicketCountResponseDto;
import com.daon.onjung.event.application.usecase.ReadTicketCountUseCase;
import com.daon.onjung.event.repository.mysql.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadTicketCountService implements ReadTicketCountUseCase {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadTicketCountResponseDto execute(UUID accountId) {

        // 유저 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(()-> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 유저가 가진 티켓 수 조회
        Long ticketCount = ticketRepository.countByUser(user);

        return ReadTicketCountResponseDto.of(ticketCount);
    }
}
