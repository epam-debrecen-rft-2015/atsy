package com.epam.rft.atsy.web.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;


import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
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
    @InjectMocks
    PositionController positionController;

    @Mock
    PositionDTO positionDTO;

    @Mock
    Collection<PositionDTO> positionDTOs;

    @Mock
    PositionService positionService;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getChannelsTest(){
        given(positionDTOs.isEmpty()).willReturn(false);
        given(positionService.getAllPositions()).willReturn(positionDTOs);
        //when
        Collection<PositionDTO> dtos = positionController.getPositions();
        //then
        assertThat(positionDTOs.isEmpty(),is(dtos.isEmpty()));
    }
}
