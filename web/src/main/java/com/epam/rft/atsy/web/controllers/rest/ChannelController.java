package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Locale;


/**
 * Created by Ikantik.
 */
@RestController
@RequestMapping(value = "/secure/channels")
public class ChannelController {
    private static final String DUPLICATE_POSITION_MESSAGE_KEY = "settings.channels.error.duplicate";
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
    public ResponseEntity<String> saveOrUpdate(@RequestBody ChannelDTO channelDTO, BindingResult result, Locale locale) {
        ResponseEntity entity = new ResponseEntity<String>("", HttpStatus.OK);

        if (!result.hasErrors()) {
            channelService.saveOrUpdate(channelDTO);
        } else {
            entity = new ResponseEntity<String>(messageSource.getMessage(EMPTY_POSITION_NAME_MESSAGE_KEY,
                    null, locale), HttpStatus.BAD_REQUEST);
        }
        return entity;
    }

    @ExceptionHandler(DuplicateRecordException.class)
    public ResponseEntity handleDuplicateException(Locale locale, DuplicateRecordException ex) {
        return new ResponseEntity<String>(messageSource.getMessage(DUPLICATE_POSITION_MESSAGE_KEY,
                new Object[]{ex.getName()}, locale), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Locale locale, Exception ex) {
        LOGGER.error("Error while saving channel changes", ex);
        return new ResponseEntity<String>(messageSource.getMessage(TECHNICAL_ERROR_MESSAGE_KEY,
                null, locale), HttpStatus.BAD_REQUEST);
    }


}
