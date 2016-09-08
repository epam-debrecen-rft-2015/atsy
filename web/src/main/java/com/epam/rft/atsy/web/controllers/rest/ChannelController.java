package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.exception.ChannelNotFoundException;
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
 * REST controller used to retrieve or modify information about the stored channels
 */
@RestController
@RequestMapping(path = "/secure/channels")
public class ChannelController {
  private static final String EMPTY_CHANNEL_NAME_MESSAGE_KEY = "settings.channels.error.empty";
  private static final String SELECTED_CHANNEL_NOT_FOUND_MESSAGE_KEY = "selected.channel.not.found";

  @Resource
  private ChannelService channelService;
  @Resource
  private MessageKeyResolver messageKeyResolver;


  /**
   * Returns all stored channels in JSON format
   * @return a collection of ChannelDTO objects
   */
  @RequestMapping(method = RequestMethod.GET)
  public Collection<ChannelDTO> getChannels() {
    return channelService.getAllChannels();
  }

  /**
   * This method is used to modify an existing channel, or add a new one to the database.
   * @param channelDTO a valid channelDTO object which stores information about the new channelDTO
   * @param result an object used to handle errors
   * @param locale a local object which sets the language of the error message (if any occurs)
   * @return a ResponseEntity object, which contains HTTP status code and error message if any
   * occurs
   */
  @RequestMapping(method = RequestMethod.POST)
  public ResponseEntity<RestResponse> saveOrUpdate(@RequestBody @Valid ChannelDTO channelDTO,
                                                   BindingResult result, Locale locale) {
    if (!result.hasErrors()) {
      channelService.saveOrUpdate(channelDTO);

      return new ResponseEntity<>(RestResponse.NO_ERROR, HttpStatus.OK);
    } else {
      String errorMessage =
          messageKeyResolver.resolveMessageOrDefault(EMPTY_CHANNEL_NAME_MESSAGE_KEY);

      RestResponse restResponse = new RestResponse(errorMessage);

      return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }
  }

  @RequestMapping(path = "/delete", method = RequestMethod.POST)
  public ResponseEntity<RestResponse> deleteChannelDtoLogicallyByName(@RequestParam(name = "channelName") String channelName) {

    try {
      channelService.deleteChannelDtoLogicallyByName(channelName);
      return new ResponseEntity<>(RestResponse.NO_ERROR, HttpStatus.OK);

    } catch (ChannelNotFoundException e) {
      String errorMessage = messageKeyResolver.resolveMessageOrDefault(SELECTED_CHANNEL_NOT_FOUND_MESSAGE_KEY);
      RestResponse restResponse = new RestResponse(errorMessage);
      return new ResponseEntity<>(restResponse, HttpStatus.BAD_REQUEST);
    }
  }
}
