package com.epam.rft.atsy.web.controllers.rest;

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.web.MediaTypes;
import com.epam.rft.atsy.web.controllers.AbstractControllerTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ChannelControllerTest extends AbstractControllerTest {
  private static final String REQUEST_URL = "/secure/channels";

  private static final Long CHANNEL_ID = 1L;

  private static final String CHANNEL_NAME = "Channel";

  @Mock
  private ChannelService channelService;

  @Mock
  private MessageSource messageSource;

  @InjectMocks
  private ChannelController channelController;

  private ChannelDTO dummyDto;

  @Before
  public void setUpTestData() {
    dummyDto = ChannelDTO.builder().id(CHANNEL_ID).name(CHANNEL_NAME).build();
  }

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{channelController};
  }

  @Test
  public void getChannelsShouldReturnEmptyJsonArrayWhenThereAreNoChannels() throws Exception {
    given(channelService.getAllChannels()).willReturn(Collections.emptyList());

    mockMvc.perform(get(REQUEST_URL))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isEmpty());
  }

  @Test
  public void getChannelsShouldReturnJsonWithOneChannelWhenThereIsOneChannel() throws Exception {
    List<ChannelDTO> channels = Collections.singletonList(dummyDto);

    given(channelService.getAllChannels()).willReturn(channels);

    mockMvc.perform(get(REQUEST_URL))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(CHANNEL_ID.intValue())))
        .andExpect(jsonPath("$[0].name", is(CHANNEL_NAME)));
  }

  @Test
  public void getChannelsShouldReturnJsonWithThreeChannelsWhenThereAreThreeChannels() throws Exception {
    List<ChannelDTO> channels = Collections.nCopies(3, dummyDto);

    given(channelService.getAllChannels()).willReturn(channels);

    mockMvc.perform(get(REQUEST_URL))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].id", is(CHANNEL_ID.intValue())))
        .andExpect(jsonPath("$[0].name", is(CHANNEL_NAME)))
        .andExpect(jsonPath("$[1].id", is(CHANNEL_ID.intValue())))
        .andExpect(jsonPath("$[1].name", is(CHANNEL_NAME)))
        .andExpect(jsonPath("$[2].id", is(CHANNEL_ID.intValue())))
        .andExpect(jsonPath("$[2].name", is(CHANNEL_NAME)));
  }


}
