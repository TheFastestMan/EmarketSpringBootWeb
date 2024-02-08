package ru.rail.emarketspringbootweb.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component

public class ValidPriceValidator implements ConstraintValidator<ValidPrice, Double> {

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value >= 0) {
            return true;
        }
        return false;
    }
}
