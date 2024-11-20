package com.daon.onjung.event.domain.service;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.type.EBankName;
import com.daon.onjung.event.domain.Event;
import com.daon.onjung.event.domain.type.EStatus;
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

    public Event createEvent(LocalDate startDate, LocalDate endDate, Store store) {

        return Event.builder()
                .startDate(startDate)
                .endDate(endDate)
                .store(store)
                .build();
    }

    public Event completeEvent(Event event) {
        event.completeEvent();
        return event;
    }
}
