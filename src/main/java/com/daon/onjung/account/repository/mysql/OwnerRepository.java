package com.daon.onjung.account.repository.mysql;

import com.daon.onjung.account.domain.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OwnerRepository extends JpaRepository <Owner, UUID> {
}
