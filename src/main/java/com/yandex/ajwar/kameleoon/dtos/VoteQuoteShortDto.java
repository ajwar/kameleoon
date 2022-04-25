package com.yandex.ajwar.kameleoon.dtos;

import com.yandex.ajwar.kameleoon.constraints.OneOrMinusOneConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteQuoteShortDto {

    @NotNull
    @Positive
    private long userId;

    @OneOrMinusOneConstraint
    private int vote;
}
