package com.daon.onjung.event.application.service;

import com.daon.onjung.core.utility.QrUtil;
import com.daon.onjung.event.application.dto.response.ReadTicketBriefResponseDto;
import com.daon.onjung.event.application.usecase.ReadTicketBriefUseCase;
import com.daon.onjung.event.repository.mysql.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReadTicketBriefService implements ReadTicketBriefUseCase {

    private final TicketRepository ticketRepository;

    private final QrUtil qrUtil;

    @Override
    @Transactional(readOnly = true)
    public ReadTicketBriefResponseDto execute(Long id) {
        byte[] qrCodeBytes = qrUtil.generateQrCodeImageByte(id);

        return ReadTicketBriefResponseDto.fromEntity(qrCodeBytes);
    }
}
