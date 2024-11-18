package com.daon.onjung.security.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.security.domain.mysql.Account;
import com.daon.onjung.security.info.KakaoOauth2UserInfo;

@UseCase
public interface ReadOrCreateUserUseCase {
    Account execute(KakaoOauth2UserInfo requestDto);
}
