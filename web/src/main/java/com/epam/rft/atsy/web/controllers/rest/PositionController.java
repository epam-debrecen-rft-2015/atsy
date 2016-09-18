package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.web.controllers.LogicallyDeletableAbstractController;
import com.epam.rft.atsy.web.exceptionhandling.RestResponse;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import javax.validation.Valid;

/**
 * A REST controller for retrieving the available positions in JSON format.
 */
@RestController
@RequestMapping(value = "/secure/positions")
public class PositionController extends LogicallyDeletableAbstractController<PositionDTO> {

  private static final String EMPTY_POSITION_NAME_MESSAGE_KEY = "settings.positions.error.empty";

  @Autowired
  public PositionController(PositionService positionService,
                            MessageKeyResolver messageKeyResolver) {
    super(positionService, messageKeyResolver);
  }

  @Override
  public ResponseEntity<RestResponse> saveOrUpdate(@RequestBody @Valid PositionDTO positionDTO,
                                                   BindingResult result, Locale locale) {
    if (!result.hasErrors()) {
      this.logicallyDeletableService.saveOrUpdate(positionDTO);

      return new ResponseEntity<>(RestResponse.NO_ERROR, HttpStatus.OK);
    } else {
      String
          errorMessage =
          this.messageKeyResolver.resolveMessageOrDefault(EMPTY_POSITION_NAME_MESSAGE_KEY);

      RestResponse restResponse = new RestResponse(errorMessage);

      return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }
  }
}
