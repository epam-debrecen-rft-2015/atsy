package com.epam.rft.atsy.web.controllers;


import com.epam.rft.atsy.service.LogicallyDeletableService;
import com.epam.rft.atsy.service.domain.LogicallyDeletableDTO;
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

import java.util.Locale;
import javax.validation.Valid;

/**
 * Abstract class that provides operations with logically deletable DTOs.
 * @param <T> the type of the object which must extend {@code LogicallyDeletableDTO}
 */
public abstract class LogicallyDeletableAbstractController<T extends LogicallyDeletableDTO> {

  public static final String SELECTED_ELEMENT_NOT_FOUND_ERROR_MESSAGE_KEY =
      "selected.element.not.found";
  protected LogicallyDeletableService logicallyDeletableService;
  protected MessageKeyResolver messageKeyResolver;

  public LogicallyDeletableAbstractController(LogicallyDeletableService logicallyDeletableService,
                                              MessageKeyResolver messageKeyResolver) {
    this.logicallyDeletableService = logicallyDeletableService;
    this.messageKeyResolver = messageKeyResolver;
  }

  /**
   * Saves or updates and existing object.
   * @param dto an object which wraps the data
   * @param bindingResult an object used to check if any error occurs
   * @param locale language of the response
   * @return a ResponseEntity object, which contains HTTP status code and error message if it occurs
   */
  @RequestMapping(method = RequestMethod.POST)
  public abstract ResponseEntity<RestResponse> saveOrUpdate(@Valid @RequestBody T dto,
                                                            BindingResult bindingResult,
                                                            Locale locale);

  /**
   * This method is used to delete an existing object from the database.
   * @param id identifier of the object that we want to delete logically
   * @return a ResponseEntity object, which contains HTTP status code and error message if any
   * occurs
   */
  @RequestMapping(path = "/delete", method = RequestMethod.DELETE)
  public ResponseEntity<RestResponse> deleteDtoLogicallyById(@RequestParam(name = "id") Long id) {
    try {
      this.logicallyDeletableService.deleteDtoLogicallyById(id);
      return new ResponseEntity<>(RestResponse.NO_ERROR, HttpStatus.OK);
    } catch (ObjectNotFoundException e) {
      String errorMessage =
          this.messageKeyResolver
              .resolveMessageOrDefault(SELECTED_ELEMENT_NOT_FOUND_ERROR_MESSAGE_KEY);

      return new ResponseEntity<>(new RestResponse(errorMessage), HttpStatus.BAD_REQUEST);
    }
  }
}
