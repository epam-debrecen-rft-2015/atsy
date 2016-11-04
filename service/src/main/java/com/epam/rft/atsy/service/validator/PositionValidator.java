package com.epam.rft.atsy.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.domain.PositionDTO;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class PositionValidator implements ConstraintValidator<PositionExists, PositionDTO>, ApplicationContextAware {

  private PositionService positionService;

  @Override
  public void initialize(PositionExists a) {
    // We leave this empty because there's no need to initialize anything.
  }

  @Override
  public boolean isValid(PositionDTO position, ConstraintValidatorContext context) {
    try {
      return positionService.getPositionDtoById(position.getId()) != null;
    } catch (AssertionError e) {
      return false;
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.positionService = applicationContext.getBean(PositionService.class);
  }

}
