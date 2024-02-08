package ru.rail.emarketspringbootweb.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Constraint(validatedBy = ValidPriceValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ValidPrice {
    String message() default "Price must be numbers";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
