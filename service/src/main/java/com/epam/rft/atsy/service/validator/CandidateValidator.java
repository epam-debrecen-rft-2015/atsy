package com.epam.rft.atsy.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.epam.rft.atsy.service.CandidateService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class CandidateValidator implements ConstraintValidator<CandidateExists, Long>, ApplicationContextAware {

  private CandidateService candidateService;

  @Override
  public void initialize(CandidateExists a) {
  }

  @Override
  public boolean isValid(Long candidateId, ConstraintValidatorContext context) {
    return candidateService.getCandidate(candidateId) != null;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.candidateService = applicationContext.getBean(CandidateService.class);
  }

}
