package com.epam.rft.atsy.web.controllers;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import com.epam.rft.atsy.service.domain.states.StateHistoryDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.ZonedDateTime;
import java.util.Date;
import lombok.val;

@RunWith(MockitoJUnitRunner.class)
public class NewApplicationControllerTest extends AbstractControllerTest {

  private static final String VIEW_NAME = "new_application_popup";
  private static final String REQUEST_URL_GET = "/new_application_popup";
  private static final String REQUEST_URL_POST = "/secure/new_application_popup";
  private static final String REDIRECT_URL_FOR_CANDIDATE_A = "/secure/candidate/1";
  private static final String REDIRECT_URL_FOR_NON_EXISTING_CANDIDATE = "/secure/candidate/null";

  private static final String CHANNEL_NAME_FACEBOOK = "facebook";
  private static final String POSITION_NAME_DEVELOPER = "Fejlesztő";
  private static final String NEW_STATE = "newstate";
  private static final String DESCRIPTION = "description";
  private static final String TEXT = "TEXT";
  private static final String STRING_VALUE_ONE = "1";

  @Mock
  private ApplicationsService applicationsService;
  @InjectMocks
  private NewApplicationController newApplicationController;

  private ChannelDTO channelDTO = ChannelDTO.builder().id(1L).name(CHANNEL_NAME_FACEBOOK).build();
  private PositionDTO positionDTO = PositionDTO.builder().id(1L).name(POSITION_NAME_DEVELOPER).build();
  private StateDTO stateDTO = StateDTO.builder().id(1L).name(NEW_STATE).build();

  private StateHistoryDTO stateHistoryDTO =
      StateHistoryDTO.builder().candidateId(1L).channel(channelDTO).position(positionDTO).stateDTO(stateDTO)
          .description(DESCRIPTION).build();

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{newApplicationController};
  }

  @Test
  public void loadPageShouldRenderNewApplicationPopupView() throws Exception {
    this.mockMvc.perform(get(REQUEST_URL_GET))
        .andExpect(status().isOk())
        .andExpect(view().name(VIEW_NAME))
        .andExpect(forwardedUrl(VIEW_PREFIX + VIEW_NAME + VIEW_SUFFIX));
  }

  @Test
  public void saveOrUpdateShouldBeSuccessful() throws Exception {
    this.mockMvc.perform(post(REQUEST_URL_POST)
        .param("candidateId", STRING_VALUE_ONE)
        .param("position.id", STRING_VALUE_ONE).param("position.name", POSITION_NAME_DEVELOPER)
        .param("channel.id", STRING_VALUE_ONE).param("channel.name", CHANNEL_NAME_FACEBOOK)
        .param("description", DESCRIPTION))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(REDIRECT_URL_FOR_CANDIDATE_A));

    val applicationDTOCaptor = ArgumentCaptor.forClass(ApplicationDTO.class);
    val stateHistoryDTOCaptor = ArgumentCaptor.forClass(StateHistoryDTO.class);

    then(applicationsService).should().saveApplication(applicationDTOCaptor.capture(), stateHistoryDTOCaptor.capture());
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
  }

  @Test
  public void saveOrUpdateShouldBeUnsuccessfulWhenPositionIdIsNotANumber() throws Exception {
    this.mockMvc.perform(post(REQUEST_URL_POST)
        .param("candidateId", STRING_VALUE_ONE)
        .param("position.id", TEXT).param("position.name", POSITION_NAME_DEVELOPER)
        .param("channel.id", STRING_VALUE_ONE).param("channel.name", CHANNEL_NAME_FACEBOOK)
        .param("description", DESCRIPTION))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(REDIRECT_URL_FOR_CANDIDATE_A));

    verifyZeroInteractions(applicationsService);
  }

  @Test
  public void saveOrUpdateShouldBeUnsuccessfulWhenChannelIdIsNotANumber() throws Exception {
    this.mockMvc.perform(post(REQUEST_URL_POST)
        .param("candidateId", STRING_VALUE_ONE)
        .param("position.id", STRING_VALUE_ONE).param("position.name", POSITION_NAME_DEVELOPER)
        .param("channel.id", TEXT).param("channel.name", CHANNEL_NAME_FACEBOOK)
        .param("description", DESCRIPTION))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(REDIRECT_URL_FOR_CANDIDATE_A));

    verifyZeroInteractions(applicationsService);
  }

  private void assertApplicationDtoWhenSaveOrUpdateIsSuccess(ArgumentCaptor<ApplicationDTO> applicationDTOCaptor) {
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
