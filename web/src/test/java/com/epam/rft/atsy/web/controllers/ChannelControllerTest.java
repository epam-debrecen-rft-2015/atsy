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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collection;
import java.util.Locale;

/**
 * Created by mates on 2015. 12. 01..
 */
public class ChannelControllerTest {

    @InjectMocks
    ChannelController channelController;

    @Mock
    ChannelDTO channelDTO;

    @Mock
    BindingResult result;

    @Mock
    ChannelService channelService;

    @Mock
    Collection<ChannelDTO> channelDTOs;

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

    
}
