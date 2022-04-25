package com.yandex.ajwar.kameleoon.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteQuoteDto extends VoteQuoteShortDto {

    @NotNull
    @Positive
    private long quoteId;
}
