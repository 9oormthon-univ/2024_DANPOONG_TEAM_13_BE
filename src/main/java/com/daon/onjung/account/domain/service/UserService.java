package com.daon.onjung.account.domain.service;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.security.domain.type.ESecurityProvider;
import com.daon.onjung.security.info.KakaoOauth2UserInfo;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    public User createUser(KakaoOauth2UserInfo requestDto, String encodedPassword) {
        String profileImageUrl = Optional.ofNullable(requestDto.kakaoAccount().profile())
                .map(KakaoOauth2UserInfo.KakaoAccountDto.KakaoProfileDto::profileImageUrl)
                .map(url -> url.startsWith("http://") ? url.replace("http://", "https://") : url)
                .orElse("NONE");

        String nickname = Optional.ofNullable(requestDto.kakaoAccount().profile())
                .map(KakaoOauth2UserInfo.KakaoAccountDto.KakaoProfileDto::nickname)
                .orElse("NONE");

        return User.builder()
                .serialId(requestDto.id())
                .password(encodedPassword)
                .provider(ESecurityProvider.KAKAO)
                .profileImgUrl(profileImageUrl)
                .nickName(nickname)
                .notificationAllowed(true)
                .build();
    }
}
