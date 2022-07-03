package com.yandex.ajwar.kameleoon.repositories;

import com.yandex.ajwar.kameleoon.configs.repositories.CustomJpaRepository;
import com.yandex.ajwar.kameleoon.entities.sql.Quote;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Stream;

@Repository
public interface QuoteRepository extends CustomJpaRepository<Quote, Long> {

    @Transactional(readOnly = true)
    @Query("select q from Quote q")
    Stream<Quote> findTop(Pageable pageable);

    @Transactional
    @Modifying
    @Query("update Quote set text=?1,updatedAt=?2 where id = ?3")
    int updateById(String text, LocalDateTime updatedAt, Long id);

    @Transactional
    @Modifying
    @Query("update Quote set numberOfVotes=numberOfVotes + ?1,updatedAt=?2 where id = ?3")
    int updateVoteById(int vote, LocalDateTime updatedAt, Long id);
}
