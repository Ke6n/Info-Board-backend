package com.info_board.validation;

import com.info_board.anno.State;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StateValidation implements ConstraintValidator<State, String> {
    /**
     *
     * @param s object to validate
     * @param constraintValidatorContext context in which the constraint is evaluated
     * @return false: Validation failed; true: Validation passed.
     */
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if(s == null){
            return false;
        }
        return s.equals("Draft") || s.equals("Published");
    }
}
