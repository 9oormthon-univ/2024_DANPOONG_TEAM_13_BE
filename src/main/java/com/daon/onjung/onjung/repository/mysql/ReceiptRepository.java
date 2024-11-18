package com.daon.onjung.onjung.repository.mysql;

import com.daon.onjung.account.domain.User;
import com.daon.onjung.onjung.domain.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReceiptRepository extends JpaRepository <Receipt, Long> {

    List<Receipt> findAllByUser(User user);
}
