package com.daon.onjung.security.config;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.account.repository.mysql.UserRepository;
import com.daon.onjung.security.domain.type.ESecurityProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class SuperUserConfig {

    @Value("${super-user.id}")
    private String superUserSerialId;

    @Value("${super-user.password}")
    private String superUserPassword;

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Bean
    public ApplicationRunner createSuperUser() {
        return args -> {

            userRepository.findBySerialId(superUserSerialId).ifPresentOrElse(
                    user -> System.out.println("슈퍼 유저가 이미 존재합니다."),
                    () -> {
                        User superUser = User.builder()
                                .provider(ESecurityProvider.DEFAULT)
                                .serialId(superUserSerialId)
                                .password(passwordEncoder.encode(superUserPassword))
                                .profileImgUrl("default.jpg")
                                .nickName("SuperUser")
                                .notificationAllowed(true)
                                .build();
                        userRepository.save(superUser);
                        System.out.println("슈퍼 유저가 생성되었습니다.");
                    }
            );
        };
    }
}
