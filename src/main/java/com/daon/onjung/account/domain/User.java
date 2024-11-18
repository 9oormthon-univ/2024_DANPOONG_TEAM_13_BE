package com.daon.onjung.account.domain;

import com.daon.onjung.security.domain.mysql.Account;
import com.daon.onjung.security.domain.type.ESecurityProvider;
import com.daon.onjung.security.domain.type.ESecurityRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "users")
@PrimaryKeyJoinColumn(
        name = "account_id",
        foreignKey = @ForeignKey(name = "fk_user_account")
)
@DiscriminatorValue("USER")
@DynamicUpdate
public class User extends Account {

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "nickname", length = 20, nullable = false)
    private String nickName;

    @Column(name = "profile_img_url", length = 2080, nullable = false)
    private String profileImgUrl;

    /* -------------------------------------------- */
    /* Timestamp Column --------------------------- */
    /* -------------------------------------------- */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public User(
            ESecurityProvider provider,
            String serialId,
            String password,
            String profileImgUrl,
            String nickName,
            Boolean notificationAllowed
    ) {
        super(
                provider,
                serialId,
                password,
                notificationAllowed
        );
        this.profileImgUrl = profileImgUrl;
        this.nickName = nickName;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public ESecurityRole getRole() {
        return ESecurityRole.USER;
    }
}
