package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Locale;


/**
 * Created by Ikantik.
 */
@org.springframework.web.bind.annotation.RestController
@RequestMapping(value = "/secure/positions")
public class PositionController {
    private static final String DUPLICATE_POSITION_MESSAGE_KEY = "settings.positions.error.duplicate";
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
    public ResponseEntity saveOrUpdate(@RequestBody PositionDTO positionDTO, Locale locale) {
        ResponseEntity entity = new ResponseEntity("", HttpStatus.OK);
        try {
            positionService.saveOrUpdate(positionDTO);
        } catch (DuplicateRecordException e) {
            entity = new ResponseEntity(messageSource.getMessage(DUPLICATE_POSITION_MESSAGE_KEY,
                    new Object[]{positionDTO.getName()}, locale), HttpStatus.BAD_REQUEST);
        } catch (Exception unhandled) {
            entity = new ResponseEntity(messageSource.getMessage(TECHNICAL_ERROR_MESSAGE_KEY,
                    null, locale), HttpStatus.BAD_REQUEST);
            LOGGER.error("Error while saving position changes", unhandled);
        }

        return entity;
    }

}
