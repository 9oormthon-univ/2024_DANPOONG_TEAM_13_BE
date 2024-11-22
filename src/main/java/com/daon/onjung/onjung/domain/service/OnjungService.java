package com.daon.onjung.onjung.domain.service;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.User;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.onjung.domain.Donation;
import com.daon.onjung.onjung.domain.Onjung;
import com.daon.onjung.onjung.domain.Receipt;
import com.daon.onjung.onjung.domain.Share;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OnjungService {

    // 기부, 영수증, 공유 객체를 받아서 온정 객체를 생성
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

    // 온정 객체를 받아서 총 온정 횟수를 계산
    public Integer calculateTotalOnjungCount(Onjung onjung) {
        return onjung.getDonations().size() + onjung.getReceipts().size() + onjung.getShares().size();
    }

    // 온정 객체를 받아서 총 온정 횟수를 계산(가게 중복 제외)
    public Integer calculateTotalUniqueOnjungStoreCount(Onjung onjung) {
        Set<Store> uniqueStores = new HashSet<>();

        // 각 리스트에서 Store 객체를 수집하여 Set에 추가
        uniqueStores.addAll(onjung.getDonations().stream()
                .map(Donation::getStore)
                .collect(Collectors.toSet()));

        uniqueStores.addAll(onjung.getReceipts().stream()
                .map(Receipt::getStore)
                .collect(Collectors.toSet()));

        uniqueStores.addAll(onjung.getShares().stream()
                .map(Share::getStore)
                .collect(Collectors.toSet()));

        return uniqueStores.size();
    }

    // 온정 객체를 받아서 총 함께한 온정인 수를 계산(중복 제외)
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
                onjung.getShares().stream()
                        .map(share -> {
                            return share.getCount() * 100;
                        })
                        .reduce(0, Integer::sum);
    }

    // 온정 객체를 받아서 생성일 기준으로 정렬
    public List<Object> sortOnjungByCreatedAt(Onjung onjung) {
        List<Object> allEntities = new ArrayList<>();
        allEntities.addAll(onjung.getDonations());
        allEntities.addAll(onjung.getReceipts());
        allEntities.addAll(onjung.getShares());

        return allEntities.stream()
                .sorted(Comparator.comparing(entity -> {
                    if (entity instanceof Donation) {
                        return ((Donation) entity).getCreatedAt();
                    } else if (entity instanceof Receipt) {
                        return ((Receipt) entity).getCreatedAt();
                    } else if (entity instanceof Share) {
                        return ((Share) entity).getCreatedAt().atStartOfDay();
                    }
                    throw new CommonException(ErrorCode.INVALID_ARGUMENT);
                }))
                .collect(Collectors.toList());
    }
}
