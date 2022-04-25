package com.yandex.ajwar.kameleoon.services;

import com.yandex.ajwar.kameleoon.dtos.IdDto;
import com.yandex.ajwar.kameleoon.dtos.QuoteDto;
import com.yandex.ajwar.kameleoon.dtos.QuoteFullDto;

import java.util.List;

public interface QuoteService {

    IdDto create(QuoteDto dto);

    void update(QuoteDto dto, Long id);

    QuoteFullDto get(Long id);

    List<QuoteFullDto> getTop(int count);

    void delete(Long id);
}
