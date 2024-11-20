package com.daon.onjung.company.domain.service;

import com.daon.onjung.company.domain.Company;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    public Company createCompany(
            String name,
            String imgUrl
    ) {
        return Company.builder()
                .name(name)
                .imgUrl(imgUrl != null ? imgUrl : "")
                .build();
    }
}
