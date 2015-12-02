package com.epam.rft.atsy.web.controllers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;


import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.web.controllers.rest.ChannelController;
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
 * Created by mates on 2015. 12. 01..
 */
public class ChannelControllerTest {
    private static final String EMPTY_POSITION_NAME_MESSAGE_KEY = "settings.channels.error.empty";


    @InjectMocks
    ChannelController channelController;

    @Mock
    ChannelDTO channelDTO;

    @Mock
    BindingResult bindingResult;

    @Mock
    BindingResult bindingResultTrue;

    @Mock
    ChannelService channelService;

    @Mock
    Collection<ChannelDTO> channelDTOs;

    @Mock
    ResponseEntity responseEntity;

    @Mock
    ResponseEntity responseEntityTrue;

    @Mock
    MessageSource messageSource;


    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getChannelsTest(){
        given(channelDTOs.isEmpty()).willReturn(false);
        given(channelService.getAllChannels()).willReturn(channelDTOs);
        //when
        Collection<ChannelDTO> dtos = channelController.getChannels();
        //then
        assertThat(channelDTOs.isEmpty(),is(dtos.isEmpty()));
    }

    @Test
    public void saveOrUpdate(){
        Locale locale=new Locale("hu");
        given(bindingResult.hasErrors()).willReturn(false);
        given(bindingResultTrue.hasErrors()).willReturn(true);
        given(channelDTO.getChannelId()).willReturn(new Long(1));
        given(channelDTO.getName()).willReturn("Email");
        //given(channelService.saveOrUpdate(channelDTO));
        given(responseEntity.getStatusCode()).willReturn(HttpStatus.OK);
        given(responseEntity.getBody()).willReturn("");
        given(responseEntityTrue.getStatusCode()).willReturn(HttpStatus.BAD_REQUEST);
        //Long candidateId=candidateService.saveOrUpdate(candidateDTO);
        ResponseEntity entity = channelController.saveOrUpdate(channelDTO,bindingResult,locale);
        ResponseEntity entityTrue = channelController.saveOrUpdate(channelDTO,bindingResultTrue,locale);

        assertThat(entity.getStatusCode(),is(HttpStatus.OK));
        assertThat(entityTrue.getStatusCode(),is(HttpStatus.BAD_REQUEST));
    }
}