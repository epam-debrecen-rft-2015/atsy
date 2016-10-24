package com.epam.rft.atsy.web.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory;

public class LazySpringConstraintValidatorFactory implements ConstraintValidatorFactory {

  private SpringConstraintValidatorFactory delegate;
  private AutowireCapableBeanFactory beanFactory;

  public void setDelegate(SpringConstraintValidatorFactory delegate) {
    this.delegate = delegate;
  }

  public void setBeanFactory(AutowireCapableBeanFactory beanFactory) {
    this.beanFactory = beanFactory;
  }

  @Override
  public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> type) {
    T result = null;
    try {
      result = this.beanFactory.getBean(type);
    } catch(BeansException e) {
      // ignore, as we initialize the validator below
    }
    if( result == null ) {
      result = this.delegate.getInstance(type);
      this.beanFactory.initializeBean(result, result.getClass() + "#" + result.hashCode());
    }
    return result;
  }

  @Override
  public void releaseInstance(ConstraintValidator<?, ?> cv) {
    this.delegate.releaseInstance(cv);
  }

}
