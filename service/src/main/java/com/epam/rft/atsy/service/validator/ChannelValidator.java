package com.epam.rft.atsy.service.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ChannelValidator implements ConstraintValidator<ChannelExists, ChannelDTO>, ApplicationContextAware {

  private ChannelService channelService;

  @Override
  public void initialize(ChannelExists a) {
    // We leave this empty because there's no need to initialize anything.
  }

  @Override
  public boolean isValid(ChannelDTO channel, ConstraintValidatorContext context) {
    return channelService.getChannelDtoById(channel.getId()) != null;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.channelService = applicationContext.getBean(ChannelService.class);
  }

}
