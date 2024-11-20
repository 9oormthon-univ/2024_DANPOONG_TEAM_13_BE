package com.daon.onjung.company.repository.mysql;

import com.daon.onjung.company.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    // company images만 반환하는 메서드
    @Query("SELECT c.imgUrl FROM Company c")
    List<String> findAllImgUrls();
}
