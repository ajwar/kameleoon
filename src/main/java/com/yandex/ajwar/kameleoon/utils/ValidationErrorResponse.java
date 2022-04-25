package com.yandex.ajwar.kameleoon.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ValidationErrorResponse {

    private final List<Violation> violations;
}
