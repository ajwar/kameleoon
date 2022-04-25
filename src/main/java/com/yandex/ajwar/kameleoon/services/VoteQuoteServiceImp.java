package com.yandex.ajwar.kameleoon.services;

import com.yandex.ajwar.kameleoon.dtos.IdDto;
import com.yandex.ajwar.kameleoon.dtos.VoteQuoteDto;
import com.yandex.ajwar.kameleoon.dtos.VoteQuoteFullDto;
import com.yandex.ajwar.kameleoon.entities.sql.VoteQuote;
import com.yandex.ajwar.kameleoon.repositories.QuoteRepository;
import com.yandex.ajwar.kameleoon.repositories.VoteQuoteRepository;
import com.yandex.ajwar.kameleoon.utils.DateConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class VoteQuoteServiceImp implements VoteQuoteService {

    private final QuoteRepository quoteRepository;
    private final VoteQuoteRepository voteQuoteRepository;
    private final ModelMapper mapper;

    @Transactional
    @Override
    public IdDto create(VoteQuoteDto dto) {
        final var now = LocalDateTime.now();
        quoteRepository.updateVoteById(dto.getVote(), now, dto.getQuoteId());
        var voteQuote = mapper.map(dto, VoteQuote.class);
        voteQuote = voteQuoteRepository.insert(voteQuote);
        return new IdDto(voteQuote.getId());
    }

    @Transactional(readOnly = true)
    @Override
    public List<VoteQuoteFullDto> getListByQuoteId(Long quoteId, Pageable pageable) {
        try (final Stream<VoteQuote> voteQuoteStream = voteQuoteRepository.findByQuoteId(quoteId, pageable)) {
            return voteQuoteStream
                    .map(voteQuote -> mapper.map(voteQuote, VoteQuoteFullDto.class))
                    .toList();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public List<VoteQuoteFullDto> getListByCreatedAt(long fromDate, long toDate, Pageable pageable) {
        final var from = DateConverter.millisToLocalDateTime(fromDate);
        final var to = DateConverter.millisToLocalDateTime(toDate);
        try (final Stream<VoteQuote> voteQuoteStream = voteQuoteRepository.findByCreatedAtBetween(from, to, pageable)) {
            return voteQuoteStream
                    .map(voteQuote -> mapper.map(voteQuote, VoteQuoteFullDto.class))
                    .toList();
        }
    }

    @Override
    public List<VoteQuoteFullDto> getListByCreatedAtAndVote(long fromDate, long toDate, int vote, Pageable pageable) {
        final var from = DateConverter.millisToLocalDateTime(fromDate);
        final var to = DateConverter.millisToLocalDateTime(toDate);
        try (final Stream<VoteQuote> voteQuoteStream =
                     voteQuoteRepository.findByCreatedAtBetweenAndVote(from, to, vote, pageable)) {
            return voteQuoteStream
                    .map(voteQuote -> mapper.map(voteQuote, VoteQuoteFullDto.class))
                    .toList();
        }
    }
}
