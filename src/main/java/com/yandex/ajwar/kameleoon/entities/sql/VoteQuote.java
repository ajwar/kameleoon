package com.yandex.ajwar.kameleoon.entities.sql;

import com.yandex.ajwar.kameleoon.entities.sql.parent.BaseLongCreatedTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static com.yandex.ajwar.kameleoon.utils.Constants.DEFAULT_SEQUENCE_ALLOCATION_SIZE;
import static com.yandex.ajwar.kameleoon.utils.Constants.NAME_GENERATOR_SEQUENCE;

@Table(name = "votes_quotes",
        indexes = {
                @Index(columnList = "quote_id, user_id", name = "quote_user_ind", unique = true),
                @Index(columnList = "vote", name = "vote_ind"),
        })
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = NAME_GENERATOR_SEQUENCE, sequenceName = "votes_quotes_id_seq", allocationSize = DEFAULT_SEQUENCE_ALLOCATION_SIZE)
public class VoteQuote extends BaseLongCreatedTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quote_id", nullable = false)
    private Quote quote;

    @Column(nullable = false)
    private int vote;
}
