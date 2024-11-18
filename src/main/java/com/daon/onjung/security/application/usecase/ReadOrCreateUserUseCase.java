package com.daon.onjung.security.application.usecase;

import com.daon.onjung.core.annotation.bean.UseCase;
import com.daon.onjung.security.domain.mysql.Account;
import com.daon.onjung.security.info.KakaoOauth2UserInfo;

@UseCase
public interface ReadOrCreateUserUseCase {

    /**
     * Security에서 Oauth Login 시 사용되는 유스케이스. 로그인한 사용자를 읽고, 없으면 생성
     * @param userInfo KakaoOauth2UserInfo
     * @return Account 읽어온 계정
     */
    Account execute(KakaoOauth2UserInfo userInfo);
}
