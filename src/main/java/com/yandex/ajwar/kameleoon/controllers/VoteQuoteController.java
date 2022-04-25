package com.yandex.ajwar.kameleoon.controllers;

import com.yandex.ajwar.kameleoon.constraints.OneOrMinusOneConstraint;
import com.yandex.ajwar.kameleoon.dtos.IdDto;
import com.yandex.ajwar.kameleoon.dtos.VoteQuoteDto;
import com.yandex.ajwar.kameleoon.dtos.VoteQuoteFullDto;
import com.yandex.ajwar.kameleoon.services.CommonService;
import com.yandex.ajwar.kameleoon.services.VoteQuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

import static com.yandex.ajwar.kameleoon.utils.Constants.MAX_SIZE_PER_PAGE;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping(path = "/v1/votes_quotes", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class VoteQuoteController {

    private final VoteQuoteService voteQuoteService;
    private final CommonService commonService;

    public static final String NAME_CREATED_AT = "createdAt";

    @PostMapping
    public IdDto create(@Valid @RequestBody VoteQuoteDto dto) {
        return voteQuoteService.create(dto);
    }

    // TODO if there are a lot of requests with filtering and sorting, then it is best to use querydsl
    @GetMapping("/quotes/{quoteId}")
    public List<VoteQuoteFullDto> getListByQuoteId(
            @PageableDefault(value = MAX_SIZE_PER_PAGE, sort = NAME_CREATED_AT, direction = DESC) Pageable pageable,
            @NotNull @Positive @PathVariable Long quoteId) {

        pageable = commonService.checkPageSize(pageable);
        return voteQuoteService.getListByQuoteId(quoteId, pageable);
    }

    // TODO if there are a lot of requests with filtering and sorting, then it is best to use querydsl
    @GetMapping("/quotes/by-date")
    public List<VoteQuoteFullDto> getListByDate(
            @PageableDefault(value = MAX_SIZE_PER_PAGE, sort = NAME_CREATED_AT, direction = ASC) Pageable pageable,
            @NotNull @RequestParam long fromDate, @NotNull @RequestParam long toDate) {

        pageable = commonService.checkPageSize(pageable);
        return voteQuoteService.getListByCreatedAt(fromDate, toDate, pageable);
    }

    // TODO if there are a lot of requests with filtering and sorting, then it is best to use querydsl
    @GetMapping("/quotes/by-date-vote")
    public List<VoteQuoteFullDto> getListByDateAndVote(
            @PageableDefault(value = MAX_SIZE_PER_PAGE, sort = NAME_CREATED_AT, direction = ASC) Pageable pageable,
            @NotNull @RequestParam long fromDate, @NotNull @RequestParam long toDate,
            @OneOrMinusOneConstraint @RequestParam int vote) {

        pageable = commonService.checkPageSize(pageable);
        return voteQuoteService.getListByCreatedAtAndVote(fromDate, toDate, vote, pageable);
    }
}
