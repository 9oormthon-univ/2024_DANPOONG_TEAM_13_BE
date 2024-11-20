package com.daon.onjung.event.domain.service;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.User;
import com.daon.onjung.event.domain.Event;
import com.daon.onjung.event.domain.Ticket;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class TicketService {

    public Ticket createTicket(
            LocalDate expirationDate,
            Integer ticketPrice,
            Boolean isValidate,
            Store store,
            User user,
            Event event
    ) {
        return Ticket.builder()
                .expirationDate(expirationDate)
                .ticketPrice(ticketPrice)
                .isValidate(isValidate)
                .store(store)
                .user(user)
                .event(event)
                .build();
    }
}
