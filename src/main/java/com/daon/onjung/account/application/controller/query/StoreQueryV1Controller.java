package com.daon.onjung.account.application.controller.query;

import com.daon.onjung.account.application.dto.response.ReadStoreOverviewsResponseDto;
import com.daon.onjung.account.application.usecase.ReadStoreOverviewUseCase;
import com.daon.onjung.account.domain.type.EOnjungTag;
import com.daon.onjung.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class StoreQueryV1Controller {

    private final ReadStoreOverviewUseCase readStoreOverviewUseCase;

    @GetMapping("/stores/overviews")
    public ResponseDto<ReadStoreOverviewsResponseDto> readStoreList(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "search", required = false) String title,
            @RequestParam(value = "tags", required = false) String onjungTags,
            @RequestParam(value = "sortByDonationCount", required = false, defaultValue = "asc") String sortByDonationCount
    ) {

        return ResponseDto.ok(
                readStoreOverviewUseCase.execute(
                        page,
                        size,
                        title,
                        onjungTags,
                        sortByDonationCount
                )
        );
    }
}
