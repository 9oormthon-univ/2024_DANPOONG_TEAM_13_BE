package com.daon.onjung.onjung.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Onjung {

    private List<Donation> donations;

    private List<Receipt> receipts;

    private List<Share> shares;

    @Builder
    public Onjung(List<Donation> donations, List<Receipt> receipts, List<Share> shares) {
        this.donations = donations;
        this.receipts = receipts;
        this.shares = shares;
    }


}
