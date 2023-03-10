package com.eyanu.tournamentproject.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PsnValidator implements ConstraintValidator<ValidPsn, String> {
    private Pattern pattern;
    private Matcher matcher;
    private static final String PSN_PATTERN = "^[A-Za-z]{1}[A-Za-z0-9-_]{2,15}$";

    @Override
    public boolean isValid(String psn, ConstraintValidatorContext constraintValidatorContext) {
        if (psn == null) {
            return true;
        }
        pattern = Pattern.compile(PSN_PATTERN);
        matcher = pattern.matcher(psn);

        return matcher.matches();
    }
}