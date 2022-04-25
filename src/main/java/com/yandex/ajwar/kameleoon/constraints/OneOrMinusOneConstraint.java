package com.yandex.ajwar.kameleoon.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = OneOrMinusOneConstraintValidator.class)
public @interface OneOrMinusOneConstraint {
    String message() default "should be equal to 1 or -1";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
