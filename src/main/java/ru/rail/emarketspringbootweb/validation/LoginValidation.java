package ru.rail.emarketspringbootweb.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
@Constraint(validatedBy = LoginValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface LoginValidation {

    String message() default "Email or password is not correct";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };
}
