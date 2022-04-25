package com.yandex.ajwar.kameleoon.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class OneOrMinusOneConstraintValidator implements ConstraintValidator<OneOrMinusOneConstraint, Integer> {
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null && (value == -1 || value == 1);
    }
}
