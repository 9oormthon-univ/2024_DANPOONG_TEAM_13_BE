package com.daon.onjung.account.application.controller.query;

import com.daon.onjung.account.application.dto.response.ReadStoreOverviewsResponseDto;
import com.daon.onjung.account.application.usecase.ReadStoreOverviewUseCase;
import com.daon.onjung.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
            @RequestParam(value = "onjungTags", required = false, defaultValue = "PATRIOT,GOOD_PRICE,UNDERFED_CHILD") String onjungTags,
            @RequestParam(value = "sortByOnjungCount", required = false, defaultValue = "desc") String sortByOnjungCount
    ) {

        return ResponseDto.ok(
                readStoreOverviewUseCase.execute(
                        page,
                        size,
                        title,
                        onjungTags,
                        sortByOnjungCount
                )
        );
    }
}
