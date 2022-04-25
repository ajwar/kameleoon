package com.yandex.ajwar.kameleoon.entities.sql.parent;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

import static com.yandex.ajwar.kameleoon.utils.Constants.NAME_GENERATOR_SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
public abstract class BaseLongIdEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = NAME_GENERATOR_SEQUENCE)
    private Long id;
}
