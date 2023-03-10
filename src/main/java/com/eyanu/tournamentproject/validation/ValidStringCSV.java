package com.eyanu.tournamentproject.validation;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ValidStringCSV {

}
