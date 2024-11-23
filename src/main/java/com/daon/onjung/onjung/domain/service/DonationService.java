package com.daon.onjung.onjung.domain.service;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.User;
import com.daon.onjung.event.domain.mysql.Event;
import com.daon.onjung.onjung.domain.Donation;
import org.springframework.stereotype.Service;

@Service
public class DonationService {

    public Donation createDonation(User user, Store store, Event event, Integer donationAmount) {
        return Donation.builder()
                .user(user)
                .store(store)
                .event(event)
                .donationAmount(donationAmount)
                .build();
    }
}
