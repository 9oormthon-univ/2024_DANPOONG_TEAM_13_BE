package com.daon.onjung.event.application.service;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.event.application.dto.response.ReadTicketResponseDto;
import com.daon.onjung.event.application.usecase.ReadTicketUseCase;
import com.daon.onjung.event.domain.Ticket;
import com.daon.onjung.event.repository.mysql.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadTicketService implements ReadTicketUseCase {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadTicketResponseDto execute(
            Integer page,
            Integer size,
            UUID accountId
    ) {

        Pageable pageable = PageRequest.of(page - 1, size);

        // 유저 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        List<Ticket> tickets = ticketRepository.findByUserOrderByExpirationDateAsc(user);

        // 티켓이 없을 경우
        if (tickets.isEmpty()) {
            return new ReadTicketResponseDto(false, List.of());
        }

        // dto로 변환
        List<ReadTicketResponseDto.TicketDto> ticketDtos = tickets.stream()
                .map(ticket -> {
                    Store store = ticket.getStore();
                    return ReadTicketResponseDto.TicketDto.fromEntity(ticket, store);
                })
                .toList();


        // 페이지네이션
        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), tickets.size());
        List<ReadTicketResponseDto.TicketDto> pagedStoreOverviewDtos = ticketDtos.subList(start, end);


        return ReadTicketResponseDto.fromPage(
                pagedStoreOverviewDtos,
                end < tickets.size()
        );
    }
}
