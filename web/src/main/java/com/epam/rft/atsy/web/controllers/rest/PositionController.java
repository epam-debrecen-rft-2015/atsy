package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.domain.PositionDTO;
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
@RequestMapping(value = "/secure/positions")
public class PositionController {
  private static final String EMPTY_POSITION_NAME_MESSAGE_KEY = "settings.positions.error.empty";
  private static final String TECHNICAL_ERROR_MESSAGE_KEY = "technical.error.message";
  private static final Logger LOGGER = LoggerFactory.getLogger(PositionController.class);

  @Resource
  private PositionService positionService;
  @Resource
  private MessageSource messageSource;

  @RequestMapping(method = RequestMethod.GET)
  public Collection<PositionDTO> getPositions() {
    return positionService.getAllPositions();
  }

  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<RestResponse> saveOrUpdate(@RequestBody PositionDTO positionDTO,
                                                   BindingResult result, Locale locale) {
    if (!result.hasErrors()) {
      positionService.saveOrUpdate(positionDTO);

      return new ResponseEntity<>(RestResponse.NO_ERROR, HttpStatus.OK);
    } else {
      String errorMessage = messageSource.getMessage(EMPTY_POSITION_NAME_MESSAGE_KEY,
          null, locale);

      RestResponse restResponse = new RestResponse(errorMessage);

      return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }
  }
}
