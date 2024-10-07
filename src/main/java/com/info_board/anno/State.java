package com.info_board.anno;

import com.info_board.validation.StateValidation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {StateValidation.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface State {
    String message() default "The value of \"state\" can only be \"Draft\" or \"Published\"";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
