package com.eyanu.tournamentproject.validation;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {

    private String firstFieldName;
    private String secondFieldName;
    private String message;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        firstFieldName = constraintAnnotation.firstField();
        secondFieldName = constraintAnnotation.secondField();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        boolean valid = true;
        try {
            final Object firstFieldValue = new BeanWrapperImpl(value).getPropertyValue(firstFieldName);
            final Object secondFieldValue = new BeanWrapperImpl(value).getPropertyValue(secondFieldName);
            valid = firstFieldValue == null && secondFieldValue == null
                    || firstFieldValue != null && firstFieldValue.equals(secondFieldValue);
        } catch (Exception e) {

        }

        if (!valid) {
            constraintValidatorContext.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(firstFieldName)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return valid;
    }
}
