package com.epam.rft.atsy.web.controllers.rest;

import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.exception.ChannelNotFoundException;
import com.epam.rft.atsy.service.exception.PositionNotFoundException;
import com.epam.rft.atsy.web.MediaTypes;
import com.epam.rft.atsy.web.controllers.AbstractControllerTest;
import com.epam.rft.atsy.web.messageresolution.MessageKeyResolver;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;

import static com.jayway.jsonassert.impl.matcher.IsCollectionWithSize.hasSize;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class PositionControllerTest extends AbstractControllerTest {

  private static final String REQUEST_URL = "/secure/positions";
  private static final String POSITION_NAME = "Fejlesztő";
  private static final String REQUEST_BODY_IS_MISSING_MESSAGE = "Required request body is missing";
  private static final String VALIDATION_ERROR_MESSAGE = "Validation error";
  private static final String EMPTY_POSITION_NAME_MESSAGE_KEY = "settings.positions.error.empty";
  private static final String SELECTED_POSITION_NOT_FOUND_MESSAGE_KEY = "selected.position.not.found.js";
  private static final String REQUEST_URL_FOR_DELETE_ = "/secure/positions/delete";
  private static final String REQUEST_PARAM_NAME_POSITION_ID = "positionId";
  private static final Long POSITION_ID = 1L;

  private PositionDTO positionDTOWithCorrectPositionName = PositionDTO.builder().id(1L).name(POSITION_NAME).build();
  private PositionDTO positionDTOWithEmptyPositionName = PositionDTO.builder().id(1L).name(StringUtils.EMPTY).build();

  @Mock
  private MessageKeyResolver messageKeyResolver;
  @Mock
  private PositionService positionService;
  @InjectMocks
  private PositionController positionController;

  @Override
  protected Object[] controllersUnderTest() {
    return new Object[]{positionController};
  }

  @Test
  public void getPositionsShouldRespondWithEmptyJsonArrayWhenThereAreNoPositions() throws Exception {
    given(positionService.getAllNonDeletedPositionDto()).willReturn(Collections.emptyList());

    this.mockMvc.perform(get(REQUEST_URL))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$").isEmpty());

    then(positionService).should().getAllNonDeletedPositionDto();
  }

  @Test
  public void getPositionsShouldRespondWithJsonWithOnePositionWhenThereIsOnePosition() throws Exception {
    given(positionService.getAllNonDeletedPositionDto())
        .willReturn(Collections.singletonList(positionDTOWithCorrectPositionName));

    this.mockMvc.perform(get(REQUEST_URL))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].id", equalTo(1)))
        .andExpect(jsonPath("$[0].name", equalTo(POSITION_NAME)));

    then(positionService).should().getAllNonDeletedPositionDto();
  }

  @Test
  public void getPositionsShouldRespondWithJsonWithThreePositionsWhenThereAreThreePositions() throws Exception {
    given(positionService.getAllNonDeletedPositionDto())
        .willReturn(Collections.nCopies(3, positionDTOWithCorrectPositionName));

    this.mockMvc.perform(get(REQUEST_URL))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].id", equalTo(1)))
        .andExpect(jsonPath("$[0].name", equalTo(POSITION_NAME)))
        .andExpect(jsonPath("$[1].id", equalTo(1)))
        .andExpect(jsonPath("$[1].name", equalTo(POSITION_NAME)))
        .andExpect(jsonPath("$[2].id", equalTo(1)))
        .andExpect(jsonPath("$[2].name", equalTo(POSITION_NAME)));

    then(positionService).should().getAllNonDeletedPositionDto();
  }

  @Test
  public void saveOrUpdateShouldRespondWithErrorResponseWhenRequestBodyIsMissing() throws Exception {

    this.mockMvc.perform(post(REQUEST_URL)
        .contentType(MediaTypes.APPLICATION_JSON_UTF8)
        .accept(MediaTypes.APPLICATION_JSON_UTF8))
        .andExpect(status().isInternalServerError())
        .andExpect(jsonPath("$.errorMessage").isNotEmpty())
        .andExpect(jsonPath("$.errorMessage", containsString(REQUEST_BODY_IS_MISSING_MESSAGE)))
        .andExpect(jsonPath("$.fields").isEmpty());
  }

  @Test
  public void saveOrUpdateShouldRespondWithValidationErrorWhenNameIsTheEmptyString() throws Exception {
    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    given(messageKeyResolver.resolveMessageOrDefault(EMPTY_POSITION_NAME_MESSAGE_KEY))
        .willReturn(VALIDATION_ERROR_MESSAGE);

    this.mockMvc.perform(post(REQUEST_URL)
        .contentType(MediaTypes.APPLICATION_JSON_UTF8)
        .accept(MediaTypes.APPLICATION_JSON_UTF8)
        .content(objectMapper.writeValueAsBytes(positionDTOWithEmptyPositionName)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.errorMessage").isNotEmpty())
        .andExpect(jsonPath("$.errorMessage", equalTo(VALIDATION_ERROR_MESSAGE)))
        .andExpect(jsonPath("$.fields").isEmpty());

    verifyZeroInteractions(positionService);
  }

  @Test
  public void saveOrUpdateShouldRespondWithNoErrorResponseWhenThePostedJsonIsCorrect() throws Exception {
    mockMvc.perform(post(REQUEST_URL)
        .contentType(MediaTypes.APPLICATION_JSON_UTF8)
        .accept(MediaTypes.APPLICATION_JSON_UTF8)
        .content(objectMapper.writeValueAsBytes(positionDTOWithCorrectPositionName)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.errorMessage").isEmpty())
        .andExpect(jsonPath("$.fields").isEmpty());

    then(positionService).should().saveOrUpdate(positionDTOWithCorrectPositionName);

    verifyZeroInteractions(messageKeyResolver);
  }

  @Test
  public void deletePositionDtoLogicallyByNameShouldRespondInternalServerErrorWhenPositionIdIsNull() throws Exception {
    doThrow(IllegalArgumentException.class).when(this.positionService)
        .deletePositionDtoLogicallyById(null);

    this.mockMvc.perform(
        delete(REQUEST_URL_FOR_DELETE_).param(REQUEST_PARAM_NAME_POSITION_ID, StringUtils.EMPTY))
        .andExpect(status().isInternalServerError());

    then(this.positionService).should().deletePositionDtoLogicallyById(null);
    verifyZeroInteractions(messageKeyResolver);
  }

  @Test
  public void deletePositionDtoLogicallyByNameShouldRespondBadRequestWhenPositionNotExists() throws Exception {
    doThrow(PositionNotFoundException.class).when(this.positionService)
        .deletePositionDtoLogicallyById(POSITION_ID);

    this.mockMvc.perform(
        delete(REQUEST_URL_FOR_DELETE_).param(REQUEST_PARAM_NAME_POSITION_ID, String.valueOf(POSITION_ID)))
        .andExpect(status().isBadRequest());

    then(this.positionService).should().deletePositionDtoLogicallyById(POSITION_ID);
    then(this.messageKeyResolver).should()
        .resolveMessageOrDefault(SELECTED_POSITION_NOT_FOUND_MESSAGE_KEY);
  }

  @Test
  public void deletePositionDtoLogicallyByNameShouldRespondOKWhenLogicallyDeletedIsSuccess() throws Exception {
    this.mockMvc.perform(
        delete(REQUEST_URL_FOR_DELETE_).param(REQUEST_PARAM_NAME_POSITION_ID, String.valueOf(POSITION_ID)))
        .andExpect(status().isOk());

    then(this.positionService).should().deletePositionDtoLogicallyById(POSITION_ID);
    verifyZeroInteractions(messageKeyResolver);
  }
}
