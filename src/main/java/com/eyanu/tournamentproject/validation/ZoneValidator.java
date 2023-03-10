package com.eyanu.tournamentproject.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.ZoneId;

public class ZoneValidator implements ConstraintValidator<ValidZone, String> {

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return ZoneId.getAvailableZoneIds().contains(s);
    }
}
