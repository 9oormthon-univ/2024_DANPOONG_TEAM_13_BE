package com.daon.onjung.onjung.repository.mysql;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.onjung.domain.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DonationRepository extends JpaRepository <Donation, Long> {

    List<Donation> findAllByUser(User user);

    List<Donation> findByUserIdAndEventId(UUID userId, Long eventId);
}
