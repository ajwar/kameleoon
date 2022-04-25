package com.yandex.ajwar.kameleoon.entities.sql;

import com.yandex.ajwar.kameleoon.entities.sql.parent.BaseLongTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static com.yandex.ajwar.kameleoon.utils.Constants.DEFAULT_SEQUENCE_ALLOCATION_SIZE;
import static com.yandex.ajwar.kameleoon.utils.Constants.NAME_GENERATOR_SEQUENCE;

@Table(name = "quotes")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = NAME_GENERATOR_SEQUENCE, sequenceName = "quotes_id_seq", allocationSize = DEFAULT_SEQUENCE_ALLOCATION_SIZE)
public class Quote extends BaseLongTimeEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String text;

    @Column(name = "number_of_votes", nullable = false, columnDefinition = "int default 0")
    private int numberOfVotes;
}
