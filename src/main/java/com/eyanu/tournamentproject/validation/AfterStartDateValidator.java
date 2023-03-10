package com.eyanu.tournamentproject.validation;

import com.eyanu.tournamentproject.model.EventForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.*;

public class AfterStartDateValidator implements ConstraintValidator<AfterStartDate, EventForm> {
    @Override
    public boolean isValid(EventForm eventForm, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = false;

        try {
            LocalTime firstTime = LocalTime.of(eventForm.getEnrollmentHour(), eventForm.getEnrollmentMinute());
            LocalTime secondTime = LocalTime.of(eventForm.getStartHour(), eventForm.getStartMinute());

            LocalDateTime firstDate = LocalDateTime.of(eventForm.getEnrollmentDeadlineDate(), firstTime);
            LocalDateTime secondDate = LocalDateTime.of(eventForm.getStartDate(), secondTime);

            valid = firstDate.isBefore(secondDate);
        }
        catch (Exception e) {
        }

        return valid;
    }
}
