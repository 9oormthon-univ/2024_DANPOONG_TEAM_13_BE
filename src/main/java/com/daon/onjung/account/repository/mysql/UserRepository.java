package com.daon.onjung.account.repository.mysql;

import com.daon.onjung.account.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository <User, UUID> {
    Optional<User> findBySerialId(String serialId);

    @Query("SELECT u.id FROM User u")
    List<UUID> findAllUserIds();

}
