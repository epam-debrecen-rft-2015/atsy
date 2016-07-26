package com.epam.rft.atsy.web.controllers;

import com.google.gson.Gson;

import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.util.JsonConverterUtil;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


public class NewApplicationPopupControllerTest {

  @Mock
  private ApplicationsService applicationsService;
  @InjectMocks
  private NewApplicationPopupController newApplicationPopupController;
  private MockMvc mockMvc;


  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    this.mockMvc = MockMvcBuilders.standaloneSetup(newApplicationPopupController).build();
  }

  @Test
  public void saveOrUpdate() throws Exception {
    ChannelDTO channelDTO = ChannelDTO.builder().id(1L).name("facebook").build();
    PositionDTO positionDTO = PositionDTO.builder().id(1L).name("Fejlesztő").build();
    StateDTO stateDTO =
        StateDTO.builder().channel(channelDTO).candidateId(1L).position(positionDTO).description("Description").build();

    Gson gson = new Gson();
    String json = gson.toJson(stateDTO);
    System.out.println(json);

    this.mockMvc.perform(post("/secure/new_application_popup").contentType(MediaType.APPLICATION_JSON)
        .content(JsonConverterUtil.convertObjectToJsonBytes(stateDTO))).andDo(print());
  }
}
