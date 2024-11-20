package com.daon.onjung.event.domain.service;

import com.daon.onjung.account.domain.type.EBankName;
import com.daon.onjung.event.domain.Event;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class EventService {

    public Event updateBankInfo(Event event, EBankName bankName, Long bankId) {
        event.updateBankInfo(bankName, bankId);
        return event;
    }

    public Integer getRestOfDate(Event event) {
        return (int) (event.getEndDate().toEpochDay() - LocalDate.now().toEpochDay());
    }
}
