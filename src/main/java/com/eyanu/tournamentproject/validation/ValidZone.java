package com.eyanu.tournamentproject.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ZoneValidator.class)
@Documented
public @interface ValidZone {
    String message() default "Invalid zone";
    Class<?>[] groups() default{};
    Class<? extends Payload>[] payload() default {};
}