package com.epam.rft.atsy.web.controllers.rest;

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.web.MediaTypes;
import com.epam.rft.atsy.web.controllers.AbstractControllerTest;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ChannelControllerTest extends AbstractControllerTest {
  private static final String REQUEST_URL = "/secure/channels";

  private static final Object MISSING_REQUEST_BODY = null;

  private static final Long CHANNEL_ID = 1L;

  private static final String CHANNEL_NAME = "Channel";

  private static final String REQUEST_BODY_IS_MISSING_MESSAGE = "Required request body is missing";

  private static final String EMPTY_POSITION_NAME_MESSAGE_KEY = "settings.channels.error.empty";

  private static final String VALIDATION_ERROR_MESSAGE = "Validation error";

  @Mock
  private ChannelService channelService;

  @Mock
  private MessageKeyResolver messageKeyResolver;

  @InjectMocks
  private ChannelController channelController;

  private ChannelDTO persistedDto;

  private ChannelDTO emptyPostedDto;

  private ChannelDTO correctPostedDto;

  @Before
  public void setUpTestData() {
    persistedDto = ChannelDTO.builder().id(CHANNEL_ID).name(CHANNEL_NAME).build();

    emptyPostedDto = ChannelDTO.builder().name(StringUtils.EMPTY).build();

    correctPostedDto = ChannelDTO.builder().name(CHANNEL_NAME).build();
  }

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{channelController};
  }

  @Test
  public void getChannelsShouldRespondWithEmptyJsonArrayWhenThereAreNoChannels() throws Exception {
    given(channelService.getAllChannels()).willReturn(Collections.emptyList());

    mockMvc.perform(get(REQUEST_URL))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isEmpty());

    then(channelService).should().getAllChannels();
  }

  @Test
  public void getChannelsShouldRespondWithJsonWithOneChannelWhenThereIsOneChannel()
      throws Exception {
    List<ChannelDTO> channels = Collections.singletonList(persistedDto);

    given(channelService.getAllChannels()).willReturn(channels);

    mockMvc.perform(get(REQUEST_URL))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", is(CHANNEL_ID.intValue())))
        .andExpect(jsonPath("$[0].name", is(CHANNEL_NAME)));

    then(channelService).should().getAllChannels();
  }

  @Test
  public void getChannelsShouldRespondWithJsonWithThreeChannelsWhenThereAreThreeChannels()
      throws Exception {
    List<ChannelDTO> channels = Collections.nCopies(3, persistedDto);

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

    then(channelService).should().getAllChannels();
  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorResponseWhenRequestBodyIsMissing()
      throws Exception {
    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, MISSING_REQUEST_BODY))
        .andExpect(status().isInternalServerError())
        .andExpect(content().contentType(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$.errorMessage").isNotEmpty())
        .andExpect(jsonPath("$.errorMessage", containsString(REQUEST_BODY_IS_MISSING_MESSAGE)))
        .andExpect(jsonPath("$.fields").isEmpty());
  }

  @Test
  public void saveOrUpdateShouldRespondWithValidationErrorWhenNameIsTheEmptyString()
      throws Exception {
    // the value of the id field in the DTO is null so tell the objectMapper
    // not to include null fields in the JSON
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

    given(messageKeyResolver.resolveMessageOrDefault(EMPTY_POSITION_NAME_MESSAGE_KEY))
        .willReturn(VALIDATION_ERROR_MESSAGE);

    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, emptyPostedDto))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorMessage").isNotEmpty())
        .andExpect(jsonPath("$.errorMessage", equalTo(VALIDATION_ERROR_MESSAGE)))
        .andExpect(jsonPath("$.fields").isEmpty());

    verifyZeroInteractions(channelService);
  }

  @Test
  public void saveOrUpdateShouldRespondWithNoErrorResponseWhenThePostedJsonIsCorrect()
      throws Exception {
    mockMvc.perform(buildJsonPostRequest(REQUEST_URL, correctPostedDto))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errorMessage").isEmpty())
        .andExpect(jsonPath("$.fields").isEmpty());

    then(channelService).should().saveOrUpdate(correctPostedDto);

    verifyZeroInteractions(messageKeyResolver);
  }
}
