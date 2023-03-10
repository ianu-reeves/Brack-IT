package com.eyanu.tournamentproject.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GamertagValidator implements ConstraintValidator<ValidGamertag, String> {
    private Pattern pattern;
    private Matcher matcher;
    private String GAMERTAG_PATTERN = "^(?=.{1,16}$)[A-Za-z]([ ?A-Za-z0-9])*$";

    @Override
    public boolean isValid(String gamertag, ConstraintValidatorContext constraintValidatorContext) {
        pattern = Pattern.compile(GAMERTAG_PATTERN);
        if (gamertag == null) {
            return true;
        }
        matcher = pattern.matcher(gamertag);

        return matcher.matches();
    }
}
