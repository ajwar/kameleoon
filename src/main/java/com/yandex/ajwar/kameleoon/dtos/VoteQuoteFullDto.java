package com.yandex.ajwar.kameleoon.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteQuoteFullDto extends VoteQuoteShortDto {

    private LocalDateTime createdAt;
}
