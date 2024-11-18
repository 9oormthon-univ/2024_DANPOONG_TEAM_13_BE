package com.daon.onjung.account.domain;

import com.daon.onjung.account.domain.type.EBankName;
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
@Table(name = "owners")
@PrimaryKeyJoinColumn(
        name = "account_id",
        foreignKey = @ForeignKey(name = "fk_owner_account")
)
@DiscriminatorValue("OWNER")
@DynamicUpdate
public class Owner extends Account {
    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "bank_account_number", length = 20, nullable = false)
    private String bankAccountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "bank_name")
    private EBankName bankName;

    /* -------------------------------------------- */
    /* One To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private Store store;

    /* -------------------------------------------- */
    /* Timestamp Column --------------------------- */
    /* -------------------------------------------- */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;


    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Owner(
            ESecurityProvider provider,
            String serialId,
            String password,
            EBankName bankName,
            String bankAccountNumber,
            Boolean notificationAllowed
    ) {
        super(
                provider,
                serialId,
                password,
                notificationAllowed
        );
        this.bankName = bankName;
        this.bankAccountNumber = bankAccountNumber;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public ESecurityRole getRole() {
        return ESecurityRole.OWNER;
    }
}