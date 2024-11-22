package com.daon.onjung.account.application.service;

import com.daon.onjung.account.application.dto.response.ReadUserOverviewsResponseDto;
import com.daon.onjung.account.application.usecase.ReadUserOverviewsUseCase;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.core.exception.error.ErrorCode;
import com.daon.onjung.core.exception.type.CommonException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReadUserOverviewsService implements ReadUserOverviewsUseCase {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public ReadUserOverviewsResponseDto execute(UUID accountId) {

        // 유저 정보 조회 후 반환
        return userRepository.findById(accountId)
                .map(ReadUserOverviewsResponseDto::fromEntity)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_RESOURCE));
    }
}
