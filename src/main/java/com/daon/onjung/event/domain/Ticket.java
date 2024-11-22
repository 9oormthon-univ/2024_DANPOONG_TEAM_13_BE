package com.daon.onjung.event.domain;

import com.daon.onjung.account.domain.Store;
import com.daon.onjung.account.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tickets")
public class Ticket {
    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "expiration_date", nullable = false)
    private LocalDate expirationDate;

    @Column(name = "ticket_price", nullable = false)
    private Integer ticketPrice;

    @Column(name = "is_validate", nullable = false)
    private Boolean isValidate;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stores_id", nullable = false)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "events_id", nullable = false)
    private Event event;

    /* -------------------------------------------- */
    /* Timestamp Column --------------------------- */
    /* -------------------------------------------- */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Ticket(LocalDate expirationDate, Integer ticketPrice, Boolean isValidate, Store store, User user, Event event) {
        this.expirationDate = expirationDate;
        this.ticketPrice = ticketPrice;
        this.isValidate = isValidate;
        this.store = store;
        this.user = user;
        this.event = event;
        this.createdAt = LocalDateTime.now();
    }

    public void updateIsValidate(Boolean isValidate) {
        this.isValidate = isValidate;
    }
}
