package com.daon.onjung.company.repository.mysql;

import com.daon.onjung.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    List<String> findAllCompanyImages();
}
