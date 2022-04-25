package com.yandex.ajwar.kameleoon.services;

import com.yandex.ajwar.kameleoon.dtos.IdDto;
import com.yandex.ajwar.kameleoon.dtos.QuoteDto;
import com.yandex.ajwar.kameleoon.dtos.QuoteFullDto;
import com.yandex.ajwar.kameleoon.entities.sql.Quote;
import com.yandex.ajwar.kameleoon.repositories.QuoteRepository;
import com.yandex.ajwar.kameleoon.repositories.VoteQuoteRepository;
import com.yandex.ajwar.kameleoon.utils.TypesUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuoteServiceImp implements QuoteService {

    private final QuoteRepository quoteRepository;
    private final VoteQuoteRepository voteQuoteRepository;
    private final ModelMapper mapper;

    private static final String NAME_NUMBER_OF_VOTES = TypesUtil.getFieldName(Quote::getNumberOfVotes);

    /**
     * TODO In a normal application with authorization and authentication,
     * TODO an authorized user from the security context would be substituted here
     */
    @Override
    public IdDto create(QuoteDto dto) {
        var quote = mapper.map(dto, Quote.class);
        quote = quoteRepository.insert(quote);
        return new IdDto(quote.getId());
    }

    /**
     * TODO In a normal application with authorization and authentication,
     * TODO there should be a check here that the authorized user edits only his quote
     */
    @Override
    public void update(QuoteDto dto, Long id) {
        final var updatedAt = LocalDateTime.now();
        quoteRepository.updateById(dto.getText(), updatedAt, id);
    }

    @Override
    public QuoteFullDto get(Long id) {
        return quoteRepository.findById(id)
                .map(quote -> mapper.map(quote, QuoteFullDto.class))
                .orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public List<QuoteFullDto> getTop(int count) {
        final var pageRequest = createPageRequestByNumberOfVotes(count);
        try (final Stream<Quote> quoteStream = quoteRepository.findTop(pageRequest)) {
            return quoteStream
                    .map(quote -> mapper.map(quote, QuoteFullDto.class))
                    .collect(Collectors.toList());
        }
    }

    /**
     * TODO here you can do a "soft delete"
     */
    @Transactional
    @Override
    public void delete(Long id) {
        final var refQuote = quoteRepository.getById(id);
        voteQuoteRepository.deleteByQuote(refQuote);
        quoteRepository.delete(refQuote);
    }

    private Pageable createPageRequestByNumberOfVotes(int count) {
        return PageRequest.of(0, count, Sort.by(Sort.Direction.DESC, NAME_NUMBER_OF_VOTES));
    }
}
