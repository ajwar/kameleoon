package com.yandex.ajwar.kameleoon.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuoteDto {

    @NotNull
    @Positive
    private long userId;

    @NotBlank
    private String text;
}
