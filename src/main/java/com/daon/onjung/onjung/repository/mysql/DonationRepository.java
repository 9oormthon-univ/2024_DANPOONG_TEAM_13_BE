package com.daon.onjung.onjung.repository.mysql;

import com.daon.onjung.onjung.domain.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository <Donation, Long> {
}
