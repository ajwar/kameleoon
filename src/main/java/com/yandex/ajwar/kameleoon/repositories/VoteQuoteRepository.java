package com.yandex.ajwar.kameleoon.repositories;

import com.yandex.ajwar.kameleoon.configs.repositories.CustomJpaRepository;
import com.yandex.ajwar.kameleoon.entities.sql.Quote;
import com.yandex.ajwar.kameleoon.entities.sql.VoteQuote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@Repository
public interface VoteQuoteRepository extends CustomJpaRepository<VoteQuote, Long> {

    @Transactional(readOnly = true)
    Stream<VoteQuote> findByQuoteId(long quoteId, Pageable pageable);

    @Transactional(readOnly = true)
    Stream<VoteQuote> findByCreatedAtBetween(LocalDateTime from, LocalDateTime to, Pageable pageable);

    @Transactional(readOnly = true)
    Stream<VoteQuote> findByCreatedAtBetweenAndVote(LocalDateTime from, LocalDateTime to, int vote, Pageable pageable);

    @Transactional
    @Modifying
    int deleteByQuote(Quote quote);
}
