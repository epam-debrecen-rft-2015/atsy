package com.epam.rft.atsy.web.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;


import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import com.epam.rft.atsy.web.controllers.rest.PositionController;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.context.MessageSource;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Locale;

/**
 * Created by mates on 2015. 12. 02..
 */
public class PositionControllerTest {
    private static final String EMPTY_POSITION_NAME_MESSAGE_KEY = "settings.positions.error.empty";


    @InjectMocks
    PositionController positionController;

    @Mock
    PositionDTO positionDTO;

    @Mock
    Collection<PositionDTO> positionDTOs;

    @Mock
    PositionService positionService;

    @Mock
    BindingResult bindingResult;

    @Mock
    BindingResult bindingResultTrue;

    @Mock
    ResponseEntity responseEntity;

    @Mock
    ResponseEntity responseEntityTrue;

    @Mock
    MessageSource messageSource;

    @Mock
    DuplicateRecordException ex;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getPositionsTest(){
        given(positionDTOs.isEmpty()).willReturn(false);
        given(positionService.getAllPositions()).willReturn(positionDTOs);
        //when
        Collection<PositionDTO> dtos = positionController.getPositions();
        //then
        assertThat(positionDTOs.isEmpty(),is(dtos.isEmpty()));
    }

    @Test
    public void saveOrUpdate(){
        Locale locale=new Locale("hu");
        given(bindingResult.hasErrors()).willReturn(false);
        given(bindingResultTrue.hasErrors()).willReturn(true);
        given(positionDTO.getPositionId()).willReturn(new Long(1));
        given(positionDTO.getName()).willReturn("Fejlesztő");
        given(responseEntity.getStatusCode()).willReturn(HttpStatus.OK);
        given(responseEntity.getBody()).willReturn("");
        given(responseEntityTrue.getStatusCode()).willReturn(HttpStatus.BAD_REQUEST);
        //given(positionService.saveOrUpdate(positionDTO));
        ResponseEntity entity = positionController.saveOrUpdate(positionDTO,bindingResult,locale);
        ResponseEntity entityTrue = positionController.saveOrUpdate(positionDTO,bindingResultTrue,locale);

        assertThat(entity.getStatusCode(),is(HttpStatus.OK));
        assertThat(entityTrue.getStatusCode(),is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void handleDuplicateException(){
        Locale locale=new Locale("hu");
        given(ex.getName()).willReturn("anything");
        given(responseEntity.getStatusCode()).willReturn(HttpStatus.BAD_REQUEST);

        ResponseEntity entity = positionController.handleDuplicateException(locale,ex);

        assertThat(entity.getStatusCode(),is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void handleException(){
        Locale locale=new Locale("hu");
        given(ex.getName()).willReturn("anything");
        given(responseEntity.getStatusCode()).willReturn(HttpStatus.BAD_REQUEST);

        ResponseEntity entity = positionController.handleException(locale,ex);

        assertThat(entity.getStatusCode(),is(HttpStatus.BAD_REQUEST));
    }

}
