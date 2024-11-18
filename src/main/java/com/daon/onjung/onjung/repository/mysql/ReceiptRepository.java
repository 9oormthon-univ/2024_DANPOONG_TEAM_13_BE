package com.daon.onjung.onjung.repository.mysql;

import com.daon.onjung.onjung.domain.Receipt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiptRepository extends JpaRepository <Receipt, Long> {
}
