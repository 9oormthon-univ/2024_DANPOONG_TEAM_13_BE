package com.daon.onjung.account.repository.mysql;

import com.daon.onjung.account.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface UserRepository extends JpaRepository <User, UUID> {
    Optional<User> findBySerialId(String serialId);
}
