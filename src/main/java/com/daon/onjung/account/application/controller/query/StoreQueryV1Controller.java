package com.daon.onjung.account.application.controller.query;

import com.daon.onjung.account.application.dto.response.ReadStoreDetailResponseDto;
import com.daon.onjung.account.application.dto.response.ReadStoreOverviewsResponseDto;
import com.daon.onjung.account.application.usecase.ReadStoreDetailUseCase;
import com.daon.onjung.account.application.usecase.ReadStoreOverviewUseCase;
import com.daon.onjung.core.annotation.security.AccountID;
import com.daon.onjung.core.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class StoreQueryV1Controller {

    private final ReadStoreOverviewUseCase readStoreOverviewUseCase;
    private final ReadStoreDetailUseCase readStoreDetailUseCase;

    /**
     * 3.1 가게 리스트 조회
     */
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

    /**
     * 3.2 가게 상세 정보 조회
     */
    @GetMapping("/stores/{id}/details")
    public ResponseDto<ReadStoreDetailResponseDto> readStoreDetail(
            @PathVariable Long id
    ) {
        return ResponseDto.ok(
                readStoreDetailUseCase.execute(id)
        );
    }
}
