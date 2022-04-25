package com.yandex.ajwar.kameleoon.entities.sql;

import com.yandex.ajwar.kameleoon.entities.sql.parent.BaseLongTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static com.yandex.ajwar.kameleoon.utils.Constants.DEFAULT_SEQUENCE_ALLOCATION_SIZE;
import static com.yandex.ajwar.kameleoon.utils.Constants.NAME_GENERATOR_SEQUENCE;

@Table(name = "users",
        indexes = {
                @Index(columnList = "email", name = "email_ind", unique = true),
        })
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name = NAME_GENERATOR_SEQUENCE, sequenceName = "users_id_seq", allocationSize = DEFAULT_SEQUENCE_ALLOCATION_SIZE)
public class User extends BaseLongTimeEntity {

    @Column(name = "email", nullable = false, length = 64)
    private String email;
}