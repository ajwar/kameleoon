package com.yandex.ajwar.kameleoon.services;

import com.yandex.ajwar.kameleoon.dtos.IdDto;
import com.yandex.ajwar.kameleoon.dtos.VoteQuoteDto;
import com.yandex.ajwar.kameleoon.dtos.VoteQuoteFullDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VoteQuoteService {

    IdDto create(VoteQuoteDto dto);

    /**
     * returns a list of user votes sorted and paginated by quote id
     */
    List<VoteQuoteFullDto> getListByQuoteId(Long quoteId, Pageable pageable);

    /**
     * returns a list of user votes sorted and paginated by time
     */
    List<VoteQuoteFullDto> getListByCreatedAt(long fromDate, long toDate, Pageable pageable);

    /**
     * returns a list of user votes sorted and paginated by time and vote
     */
    List<VoteQuoteFullDto> getListByCreatedAtAndVote(long fromDate, long toDate, int vote, Pageable pageable);
}
