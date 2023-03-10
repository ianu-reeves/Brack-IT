package com.eyanu.tournamentproject.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(value = {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PsnValidator.class)
@Documented
public @interface ValidPsn {
    String message() default "Invalid PlayStation Network (PSN) ID";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default{};
}
