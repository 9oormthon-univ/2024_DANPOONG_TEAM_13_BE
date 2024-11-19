package com.daon.onjung.onjung.application.service;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.StoreRepository;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import com.daon.onjung.onjung.application.usecase.CreateOrUpdateShareUseCase;
import com.daon.onjung.onjung.domain.Share;
import com.daon.onjung.onjung.domain.service.ShareService;
import com.daon.onjung.onjung.repository.mysql.ShareRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CreateOrUpdateShareService implements CreateOrUpdateShareUseCase {

    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ShareRepository shareRepository;

    private final ShareService shareService;

    @Override
    @Transactional
    public Boolean execute(UUID accountId, Long storeId) {

        // 유저 조회
        User user = userRepository.findById(accountId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 가게 조회
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));

        // 공유 정보 조회
        Share share = shareRepository.findByUserAndStore(user, store)
                .orElse(null);
        Boolean isCreated = (share == null);

        // 공유 정보 생성 또는 Count 1 추가
        share = shareService.createOrUpdateShare(share, user, store);
        shareRepository.save(share);

        return isCreated;
    }
}
