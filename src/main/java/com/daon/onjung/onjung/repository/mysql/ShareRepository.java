package com.daon.onjung.onjung.repository.mysql;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.onjung.domain.Share;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShareRepository extends JpaRepository <Share, Long> {

    List<Share> findAllByUser(User user);
}
