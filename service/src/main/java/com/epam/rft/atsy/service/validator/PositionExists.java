package com.epam.rft.atsy.service.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = {PositionValidator.class})
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PositionExists {

  String message();
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
