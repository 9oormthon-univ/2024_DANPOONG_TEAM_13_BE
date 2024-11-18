package com.daon.onjung.onjung.domain.service;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.onjung.domain.Donation;
import com.daon.onjung.onjung.domain.Onjung;
import com.daon.onjung.onjung.domain.Receipt;
import com.daon.onjung.onjung.domain.Share;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class OnjungService {

    public Onjung createOnjung(
            List<Donation> donations,
            List<Receipt> receipts,
            List<Share> shares
    ) {
        return Onjung.builder()
                .donations(donations)
                .receipts(receipts)
                .shares(shares)
                .build();
    }

    public Integer calculateTotalOnjungCount(Onjung onjung) {
        return onjung.getDonations().size() + onjung.getReceipts().size() + onjung.getShares().size();
    }

    public Integer calculateTotalUniqueOnjungUserCount(Onjung onjung) {
        Set<User> uniqueUsers = new HashSet<>();

        // 각 리스트에서 User 객체를 수집하여 Set에 추가
        uniqueUsers.addAll(onjung.getDonations().stream()
                .map(Donation::getUser)
                .collect(Collectors.toSet()));

        uniqueUsers.addAll(onjung.getReceipts().stream()
                .map(Receipt::getUser)
                .collect(Collectors.toSet()));

        uniqueUsers.addAll(onjung.getShares().stream()
                .map(Share::getUser)
                .collect(Collectors.toSet()));

        return uniqueUsers.size();
    }

    public Integer calculateTotalOnjungAmount(Onjung onjung) {
        return onjung.getDonations().stream()
                .map(Donation::getDonationAmount)
                .reduce(0, Integer::sum) +
                onjung.getReceipts().stream()
                        .map(Receipt::getPaymentAmount)
                        .reduce(0, Integer::sum) +
                onjung.getShares().size() * 100;
    }
}
