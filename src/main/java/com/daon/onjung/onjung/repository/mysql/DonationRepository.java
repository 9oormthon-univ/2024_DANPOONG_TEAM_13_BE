package com.daon.onjung.onjung.repository.mysql;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.onjung.domain.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository <Donation, Long> {

    List<Donation> findAllByUser(User user);
}
