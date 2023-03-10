package com.eyanu.tournamentproject.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(value = {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GamertagValidator.class)
@Documented
public @interface ValidGamertag {
    String message() default "Invalid gamertag";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
