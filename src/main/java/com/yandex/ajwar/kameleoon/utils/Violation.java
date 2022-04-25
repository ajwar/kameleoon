package com.yandex.ajwar.kameleoon.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Violation {

    private final String fieldName;
    private final String msg;
}
