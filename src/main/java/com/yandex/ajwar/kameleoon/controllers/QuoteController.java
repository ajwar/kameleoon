package com.yandex.ajwar.kameleoon.controllers;

import com.yandex.ajwar.kameleoon.dtos.IdDto;
import com.yandex.ajwar.kameleoon.dtos.QuoteDto;
import com.yandex.ajwar.kameleoon.dtos.QuoteFullDto;
import com.yandex.ajwar.kameleoon.services.QuoteService;
import com.yandex.ajwar.kameleoon.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping(path = "/v1/quotes", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class QuoteController {

    private final QuoteService quoteService;

    @PostMapping
    public IdDto create(@Valid @RequestBody QuoteDto dto) {
        return quoteService.create(dto);
    }

    @PutMapping("/{id}")
    public void update(@NotNull @Positive @PathVariable Long id, @Valid @RequestBody QuoteDto dto) {
        quoteService.update(dto, id);
    }

    @GetMapping("/{id}")
    public QuoteFullDto get(@NotNull @Positive @PathVariable Long id) {
        return quoteService.get(id);
    }

    @GetMapping("/top10")
    public List<QuoteFullDto> getTop10() {
        return quoteService.getTop(Constants.SIZE_PER_PAGE_QUOTE);
    }

    @DeleteMapping("/{id}")
    public void delete(@NotNull @Positive @PathVariable Long id) {
        quoteService.delete(id);
    }
}
