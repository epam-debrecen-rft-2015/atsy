package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.web.exceptionhandling.RestResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Locale;
import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/secure/channels")
public class ChannelController {
  private static final String EMPTY_POSITION_NAME_MESSAGE_KEY = "settings.channels.error.empty";
  private static final String TECHNICAL_ERROR_MESSAGE_KEY = "technical.error.message";
  private static final Logger LOGGER = LoggerFactory.getLogger(ChannelController.class);

  @Resource
  private ChannelService channelService;
  @Resource
  private MessageSource messageSource;

  @RequestMapping(method = RequestMethod.GET)
  public Collection<ChannelDTO> getChannels() {
    return channelService.getAllChannels();
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<RestResponse> saveOrUpdate(@RequestBody ChannelDTO channelDTO,
                                                   BindingResult result, Locale locale) {
    if (!result.hasErrors()) {
      channelService.saveOrUpdate(channelDTO);

      return new ResponseEntity<>(RestResponse.NO_ERROR, HttpStatus.OK);
    } else {
      String errorMessage = messageSource.getMessage(EMPTY_POSITION_NAME_MESSAGE_KEY,
          null, locale);

      RestResponse restResponse = new RestResponse(errorMessage);

      return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }
  }
}
