package com.epam.rft.atsy.web.controllers;

import static org.mockito.BDDMockito.then;

import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.CandidateService;
import com.epam.rft.atsy.service.ChannelService;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import lombok.val;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(MockitoJUnitRunner.class)

public class NewApplicationControllerTest extends AbstractControllerTest {

  private static final String VIEW_NAME = "application";
  private static final String REQUEST_URL_GET = "/new_application";
  private static final String REQUEST_URL_POST = "/secure/new_application";
  private static final String REDIRECT_URL_FOR_CANDIDATE_A = "/secure/candidate/1";
  private static final String REDIRECT_URL_FOR_NON_EXISTING_CANDIDATE = "/secure/application?candidateId=null";
  private static final String REDIRECT_URL_FOR_CANDIDATE_WITH_WRONG_ID = "/secure/application?candidateId=2";
  private static final String REDIRECT_URL_FIELD_ERROR = "/secure/application?candidateId=1";

  private static final String CHANNEL_NAME_FACEBOOK = "facebook";
  private static final String POSITION_NAME_DEVELOPER = "Fejlesztő";
  private static final String NEW_STATE = "newstate";
  private static final String DESCRIPTION = "description";
  private static final String TEXT = "TEXT";
  private static final String STRING_VALUE_ONE = "1";
  private static final String STRING_VALUE_TWO = "2";

  private static final String COMMON_INVALID_INPUT_MESSAGE_KEY = "common.invalid.input";
  private static final String COMMON_INVALID_INPUT_MESSAGE = "One or more fields contain incorrect input!";
  private static final String CANDIDATE_NOT_FOUND_ERROR_MESSAGE_KEY = "candidate.not.found.error.message";
  private static final String CANDIDATE_NOT_FOUND_ERROR_MESSAGE = "The given candidate does not exist!";
  private static final String POSITION_NOT_FOUND_ERROR_MESSAGE_KEY = "position.not.found.error.message";
  private static final String POSITION_NOT_FOUND_ERROR_MESSAGE = "The given position does not exist!";
  private static final String CHANNEL_NOT_FOUND_ERROR_MESSAGE_KEY = "channel.not.found.error.message";
  private static final String CHANNEL_NOT_FOUND_ERROR_MESSAGE = "The given channel does not exist!";

  @Mock
  private ApplicationsService applicationsService;
  @Mock
  private CandidateService candidateService;
  @Mock
  private PositionService positionService;
  @Mock
  private ChannelService channelService;
  @Mock
  private MessageKeyResolver messageKeyResolver;
  @InjectMocks
  private NewApplicationController newApplicationController;

  private ChannelDTO
      channelDTO =
      ChannelDTO.builder().id(1L).name(CHANNEL_NAME_FACEBOOK).deleted(false).build();

  private PositionDTO
      positionDTO =
      PositionDTO.builder().id(1L).name(POSITION_NAME_DEVELOPER).deleted(false).build();
  private StateDTO stateDTO = StateDTO.builder().id(1L).name(NEW_STATE).build();

  private StateHistoryDTO stateHistoryDTO =
      StateHistoryDTO.builder().candidateId(1L).channel(channelDTO).position(positionDTO)
          .stateDTO(stateDTO).description(DESCRIPTION).build();

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{newApplicationController};
  }

  @Override
  protected void addBeans(List<Object> beans) {
    beans.add(this.candidateService);
    beans.add(this.positionService);
    beans.add(this.channelService);
  }

  @Test
  public void loadPageShouldRenderNewApplicationView() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL_GET))
        .andExpect(status().isOk())
        .andExpect(view().name(VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX));
  }

  @Test
  public void saveOrUpdateShouldBeSuccessful() throws Exception {   
    given(this.candidateService.getCandidate(1L)).willReturn(new CandidateDTO());
    given(this.positionService.getPositionDtoById(1L)).willReturn(positionDTO);
    given(this.channelService.getChannelDtoById(1L)).willReturn(channelDTO);

    this.mockMvc.perform(post(REQUEST_URL_POST)
        .param("candidateId", STRING_VALUE_ONE)
        .param("position.id", STRING_VALUE_ONE).param("position.name", POSITION_NAME_DEVELOPER)
        .param("channel.id", STRING_VALUE_ONE).param("channel.name", CHANNEL_NAME_FACEBOOK)
        .param("description", DESCRIPTION))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(REDIRECT_URL_FOR_CANDIDATE_A));

    val applicationDTOCaptor = ArgumentCaptor.forClass(ApplicationDTO.class);
    val stateHistoryDTOCaptor = ArgumentCaptor.forClass(StateHistoryDTO.class);

    verify(applicationsService).saveApplication(applicationDTOCaptor.capture(), stateHistoryDTOCaptor.capture());
    then(applicationsService).should()
        .saveApplication(applicationDTOCaptor.getValue(), stateHistoryDTOCaptor.getValue());
    then(this.candidateService).should().getCandidate(1L);
    then(this.positionService).should().getPositionDtoById(1L);
    then(this.channelService).should().getChannelDtoById(1L);

    assertThat(stateHistoryDTOCaptor.getValue(), equalTo(stateHistoryDTO));
    assertApplicationDtoWhenSaveOrUpdateIsSuccess(applicationDTOCaptor);
  }

  @Test
  public void saveOrUpdateShouldBeUnsuccessfulWhenCandidateIdIsNotANumber() throws Exception {

    this.mockMvc.perform(post(REQUEST_URL_POST)
        .param("candidateId", TEXT)
        .param("position.id", STRING_VALUE_ONE).param("position.name", POSITION_NAME_DEVELOPER)
        .param("channel.id", STRING_VALUE_ONE).param("channel.name", CHANNEL_NAME_FACEBOOK)
        .param("description", DESCRIPTION))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(REDIRECT_URL_FOR_NON_EXISTING_CANDIDATE));

    verifyZeroInteractions(applicationsService);
    then(this.candidateService).should().getCandidate(null);
    then(this.positionService).should().getPositionDtoById(1L);
    then(this.channelService).should().getChannelDtoById(1L);
  }

  @Test
  public void saveOrUpdateShouldBeUnsuccessfulWhenCandidateIdNotExists() throws Exception {
    given(this.messageKeyResolver.resolveMessageOrDefault(COMMON_INVALID_INPUT_MESSAGE_KEY)).willReturn(COMMON_INVALID_INPUT_MESSAGE);
    given(this.messageKeyResolver.resolveMessageOrDefault(CANDIDATE_NOT_FOUND_ERROR_MESSAGE_KEY)).willReturn(CANDIDATE_NOT_FOUND_ERROR_MESSAGE);

    this.mockMvc.perform(post(REQUEST_URL_POST)
        .param("candidateId", STRING_VALUE_TWO)
        .param("position.id", STRING_VALUE_ONE).param("position.name", POSITION_NAME_DEVELOPER)
        .param("channel.id", STRING_VALUE_ONE).param("channel.name", CHANNEL_NAME_FACEBOOK)
        .param("description", DESCRIPTION))
        .andExpect(status().is3xxRedirection())
        .andExpect(flash().attribute("candidateIdErrorMessage", CANDIDATE_NOT_FOUND_ERROR_MESSAGE))
        .andExpect(redirectedUrl(REDIRECT_URL_FOR_CANDIDATE_WITH_WRONG_ID));

    verifyZeroInteractions(applicationsService);
    then(this.candidateService).should().getCandidate(2L);
    then(this.positionService).should().getPositionDtoById(1L);
    then(this.channelService).should().getChannelDtoById(1L);
  }

  @Test
  public void saveOrUpdateShouldBeUnsuccessfulWhenPositionIdIsNotANumber() throws Exception {
    given(this.messageKeyResolver.resolveMessageOrDefault(COMMON_INVALID_INPUT_MESSAGE_KEY)).willReturn(COMMON_INVALID_INPUT_MESSAGE);
    given(this.messageKeyResolver.resolveMessageOrDefault(POSITION_NOT_FOUND_ERROR_MESSAGE_KEY)).willReturn(POSITION_NOT_FOUND_ERROR_MESSAGE);

    this.mockMvc.perform(post(REQUEST_URL_POST)
        .param("candidateId", STRING_VALUE_ONE)
        .param("position.id", TEXT).param("position.name", POSITION_NAME_DEVELOPER)
        .param("channel.id", STRING_VALUE_ONE).param("channel.name", CHANNEL_NAME_FACEBOOK)
        .param("description", DESCRIPTION))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(REDIRECT_URL_FIELD_ERROR))
        .andExpect(flash().attribute("positionErrorMessage", POSITION_NOT_FOUND_ERROR_MESSAGE));

    verifyZeroInteractions(applicationsService);
    then(this.candidateService).should().getCandidate(1L);
    then(this.positionService).should().getPositionDtoById(null);
    then(this.channelService).should().getChannelDtoById(1L);
  }

  @Test
  public void saveOrUpdateShouldBeUnsuccessfulWhenChannelIdIsNotANumber() throws Exception {
    given(this.messageKeyResolver.resolveMessageOrDefault(COMMON_INVALID_INPUT_MESSAGE_KEY)).willReturn(COMMON_INVALID_INPUT_MESSAGE);
    given(this.messageKeyResolver.resolveMessageOrDefault(CHANNEL_NOT_FOUND_ERROR_MESSAGE_KEY)).willReturn(CHANNEL_NOT_FOUND_ERROR_MESSAGE);

    this.mockMvc.perform(post(REQUEST_URL_POST)
        .param("candidateId", STRING_VALUE_ONE)
        .param("position.id", STRING_VALUE_ONE).param("position.name", POSITION_NAME_DEVELOPER)
        .param("channel.id", TEXT).param("channel.name", CHANNEL_NAME_FACEBOOK)
        .param("description", DESCRIPTION))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(REDIRECT_URL_FIELD_ERROR))
        .andExpect(flash().attribute("channelErrorMessage", CHANNEL_NOT_FOUND_ERROR_MESSAGE));

    verifyZeroInteractions(applicationsService);
    then(this.candidateService).should().getCandidate(1L);
    then(this.positionService).should().getPositionDtoById(1L);
    then(this.channelService).should().getChannelDtoById(null);
  }

  private void assertApplicationDtoWhenSaveOrUpdateIsSuccess(
      ArgumentCaptor<ApplicationDTO> applicationDTOCaptor) {
    Date presentDate = currentDateMinus(5);
    assertThat(applicationDTOCaptor.getValue().getCandidateId(), equalTo(1L));
    assertThat(applicationDTOCaptor.getValue().getChannelId(), equalTo(1L));
    assertThat(applicationDTOCaptor.getValue().getPositionId(), equalTo(1L));
    assertThat(applicationDTOCaptor.getValue().getCreationDate(), greaterThan(presentDate));
  }

  private Date currentDateMinus(long seconds) {
    return Date.from(ZonedDateTime.now().minusSeconds(seconds).toInstant());
  }
}
