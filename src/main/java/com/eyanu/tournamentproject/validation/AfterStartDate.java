package com.eyanu.tournamentproject.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AfterStartDateValidator.class)
@Documented

public @interface AfterStartDate {
    String message() default "Later date cannot come before the earlier date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @interface List {
        AfterStartDate[] value();
    }
}
