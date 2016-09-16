package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.exception.ObjectNotFoundException;
import com.epam.rft.atsy.web.exceptionhandling.RestResponse;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.Locale;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * A REST controller for retrieving the available positions in JSON format.
 */
@RestController
@RequestMapping(value = "/secure/positions")
public class PositionController {
  private static final String EMPTY_POSITION_NAME_MESSAGE_KEY = "settings.positions.error.empty";
  private static final String SELECTED_POSITION_NOT_FOUND_MESSAGE_KEY = "selected.position.not.found.js";

  @Resource
  private PositionService positionService;
  @Resource
  private MessageKeyResolver messageKeyResolver;

  /**
   * Get all the available positions.
   *
   * @return a collection of the available positions wrapped in PositionDTO objects
   */
  @RequestMapping(method = RequestMethod.GET)
  public Collection<PositionDTO> getPositions() {
    return positionService.getAllNonDeletedDto();
  }

  /**
   * Saves or updates and existing position.
   *
   * @param positionDTO an object which wraps the data of a position
   * @param result      an object used to check if any error occurs
   * @param locale      language of the response
   * @return a ResponseEntity object, which contains HTTP status code and error message if it occurs
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<RestResponse> saveOrUpdate(@RequestBody @Valid PositionDTO positionDTO,
                                                   BindingResult result, Locale locale) {
    if (!result.hasErrors()) {
      positionService.saveOrUpdate(positionDTO);

      return new ResponseEntity<>(RestResponse.NO_ERROR, HttpStatus.OK);
    } else {
      String errorMessage = messageKeyResolver.resolveMessageOrDefault(EMPTY_POSITION_NAME_MESSAGE_KEY);

      RestResponse restResponse = new RestResponse(errorMessage);

      return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }
  }

  @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
  public ResponseEntity<RestResponse> deletePositionDtoLogicallyByName(@RequestParam(name = "positionId") Long positionId) {

    try {
      positionService.deleteDtoLogicallyById(positionId);
      return new ResponseEntity<>(RestResponse.NO_ERROR, HttpStatus.OK);

    } catch (ObjectNotFoundException e) {
      String errorMessage = messageKeyResolver.resolveMessageOrDefault(SELECTED_POSITION_NOT_FOUND_MESSAGE_KEY);

      RestResponse restResponse = new RestResponse(errorMessage);
      return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }
  }
}
