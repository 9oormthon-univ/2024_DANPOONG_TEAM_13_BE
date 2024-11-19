package com.daon.onjung.onjung.domain.service;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.User;
import com.daon.onjung.onjung.domain.Share;
import org.springframework.stereotype.Service;

@Service
public class ShareService {

    public Share createOrUpdateShare(Share share, User user, Store store) {

        // 해당 가게에 대해 공유한 적이 없는 경우
        if (share == null) {
            share = Share.builder()
                    .user(user)
                    .store(store)
                    .build();
        } else { // 해당 가게에 대해 공유한 적이 있는 경우
            share.addCount();
        }
        return share;
    }
}
