package com.epam.rft.atsy.service.impl;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verifyZeroInteractions;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.exception.DuplicateCandidateException;
import com.epam.rft.atsy.service.request.CandidateFilterRequest;
import com.epam.rft.atsy.service.response.PagingResponse;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class CandidateServiceImplTest {
  private static final Long ID = 1L;
  private static final Long APPLICATION_ID = 1L;
  private static final Long NON_EXISTENT_APPLICATION_ID = 2L;
  private static final String NAME = "John Doe";
  private static final String EMAIL = "john@doe.com";
  private static final String PHONE = "123456";
  private static final String REFERER = "Jane Doe";
  private static final Short LANGUAGE_SKILL = 5;
  private static final String DESCRIPTION = "Simply John Doe.";
  private static final CandidateEntity NOT_FOUND_CANDIDATE_ENTITY = null;
  private static final CandidateDTO NOT_FOUND_CANDIDATE_DTO = null;

  private static final String SORT_NAME_NAME = "name";
  private static final String SORT_ORDER_ASC = "asc";

  private static final String INVALID_SORT_ORDER = "invalid sort order";
  private static final String INVALID_SORT_NAME = "invalid sort name";

  private static final int DEFAULT_PAGE_NUMBER = 0;
  private static final int DEFAULT_PAGE_SIZE = 10;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Mock
  private ConverterService converterService;

  @Mock
  private CandidateRepository candidateRepository;

  @Mock
  private ApplicationsRepository applicationsRepository;

  @InjectMocks
  private CandidateServiceImpl candidateService;

  private CandidateEntity dummyCandidateEntity;
  private CandidateDTO dummyCandidateDto;
  private ApplicationEntity dummyApplicationEntity;

  @Before
  public void setUp() {
    dummyCandidateEntity = CandidateEntity.builder().id(ID).name(NAME).email(EMAIL).phone(PHONE)
        .referer(REFERER).languageSkill(LANGUAGE_SKILL).description(DESCRIPTION).build();

    dummyApplicationEntity =
        ApplicationEntity.builder().id(APPLICATION_ID).candidateEntity(dummyCandidateEntity)
            .build();

    dummyCandidateDto = CandidateDTO.builder().id(ID).name(NAME).email(EMAIL).phone(PHONE)
        .referer(REFERER).languageSkill(LANGUAGE_SKILL).description(DESCRIPTION).build();
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCandidateShouldThrowIAEWhenIdIsNull() {
    // When
    candidateService.getCandidate(null);
  }

  @Test
  public void getCandidateShouldReturnNullWhenIdNotFound() {
    // Given
    given(candidateRepository.findOne(ID)).willReturn(NOT_FOUND_CANDIDATE_ENTITY);
    given(converterService.convert(NOT_FOUND_CANDIDATE_ENTITY, CandidateDTO.class))
        .willReturn(NOT_FOUND_CANDIDATE_DTO);

    // When
    CandidateDTO candidate = candidateService.getCandidate(ID);

    // Then
    assertThat(candidate, nullValue());
  }

  @Test
  public void getCandidateShouldReturnProperDtoWhenIdExists() {
    // Given
    given(candidateRepository.findOne(ID)).willReturn(dummyCandidateEntity);
    given(converterService.convert(dummyCandidateEntity, CandidateDTO.class))
        .willReturn(dummyCandidateDto);

    // When
    CandidateDTO candidate = candidateService.getCandidate(ID);

    // Then
    assertThat(candidate, equalTo(dummyCandidateDto));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCandidateByApplicationIDShouldThrowIllegalArgumentExceptionWhenApplicationIDIsNull() {
    // When
    candidateService.getCandidateByApplicationID(null);
  }

  @Test
  public void getCandidateByApplicationIdShouldReturnNullWhenThereIsNoApplicationWithTheGivenId() {
    // Given
    given(applicationsRepository.findOne(NON_EXISTENT_APPLICATION_ID)).willReturn(null);

    // When
    CandidateDTO
        candidate =
        candidateService.getCandidateByApplicationID(NON_EXISTENT_APPLICATION_ID);

    // Then
    assertThat(candidate, nullValue());
  }

  @Test
  public void getCandidateByApplicationIdShouldReturnCandidateDTOWhenThereIsApplicationWithTheGivenId() {
    // Given
    given(applicationsRepository.findOne(APPLICATION_ID))
        .willReturn(dummyApplicationEntity);
    given(converterService.convert(dummyCandidateEntity, CandidateDTO.class))
        .willReturn(dummyCandidateDto);

    // When
    CandidateDTO
        candidate =
        candidateService.getCandidateByApplicationID(APPLICATION_ID);

    // Then
    assertThat(candidate, equalTo(dummyCandidateDto));
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCandidatesByFilterRequestShouldThrowIllegalArgumentExceptionWhenCandidateFilterRequestIsnull() {
    // Given

    // When
    candidateService.getCandidatesByFilterRequest(null);
    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCandidatesByFilterRequestShouldThrowIllegalArgumentExceptionWhenPageSizeIsnull() {
    // Given
    CandidateFilterRequest
        candidateFilterRequest =
        CandidateFilterRequest.builder().pageSize(null).build();
    // When
    candidateService.getCandidatesByFilterRequest(candidateFilterRequest);
    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCandidatesByFilterRequestShouldThrowIllegalArgumentExceptionWhenPageNumberIsnull() {
    // Given
    CandidateFilterRequest
        candidateFilterRequest =
        CandidateFilterRequest.builder().pageNumber(null).build();
    // When
    candidateService.getCandidatesByFilterRequest(candidateFilterRequest);
    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCandidatesByFilterRequestShouldThrowIllegalArgumentExceptionWhenSortNameIsNotNullButSortOrderIsNull() {
    // Given
    CandidateFilterRequest
        candidateFilterRequest =
        CandidateFilterRequest.builder().sortName(SORT_NAME_NAME).sortOrder(null).build();
    // When
    candidateService.getCandidatesByFilterRequest(candidateFilterRequest);
    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCandidatesByFilterRequestShouldThrowIllegalArgumentExceptionWhenSortOrderIsNotNullButSortNameIsNull() {
    // Given
    CandidateFilterRequest
        candidateFilterRequest =
        CandidateFilterRequest.builder().sortOrder(SORT_ORDER_ASC).sortName(null).build();
    // When
    candidateService.getCandidatesByFilterRequest(candidateFilterRequest);
    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCandidatesByFilterRequestShouldThrowIllegalArgumentExceptionWhenSortOrderIsInvalid() {
    // Given
    CandidateFilterRequest
        candidateFilterRequest =
        CandidateFilterRequest.builder().sortName(SORT_NAME_NAME).sortOrder(INVALID_SORT_ORDER)
            .build();
    // When
    candidateService.getCandidatesByFilterRequest(candidateFilterRequest);
    // Then
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCandidatesByFilterRequestShouldThrowIllegalArgumentExceptionWhenSortNameIsInvalid() {
    // Given
    CandidateFilterRequest
        candidateFilterRequest =
        CandidateFilterRequest.builder().sortName(INVALID_SORT_NAME).sortOrder(SORT_ORDER_ASC)
            .build();
    // When
    candidateService.getCandidatesByFilterRequest(candidateFilterRequest);
    // Then
  }

  @Test
  public void getCandidatesByFilterRequestShouldFindDummyCandidateDTOInAscendingOrderByNameWhenThereIsARightCandidateFilterRequest() {
    // Given

    final CandidateFilterRequest
        candidateFilterRequest =
        CandidateFilterRequest.builder().pageNumber(DEFAULT_PAGE_NUMBER).pageSize(DEFAULT_PAGE_SIZE)
            .sortName(SORT_NAME_NAME).sortOrder(SORT_ORDER_ASC).candidateName(NAME).build();

    final PageRequest
        pageRequest =
        new PageRequest(candidateFilterRequest.getPageNumber(),
            candidateFilterRequest.getPageSize(),
            Sort.Direction.fromString(candidateFilterRequest.getSortOrder()),
            candidateFilterRequest.getSortName());

    final String name = candidateFilterRequest.getCandidateName();
    final String email = StringUtils.EMPTY;
    final String phone = StringUtils.EMPTY;
    final String positions = StringUtils.EMPTY;

    final Page<CandidateEntity>
        dummyCandidateEntityPage =
        new PageImpl<>(Arrays.asList(dummyCandidateEntity));

    final List<CandidateDTO> dummyCandidateDTOs = Arrays.asList(dummyCandidateDto);

    given(candidateRepository
        .findByCandidateFilterRequest(name, email, phone, positions, pageRequest))
        .willReturn(dummyCandidateEntityPage);

    given(converterService.convert(dummyCandidateEntityPage.getContent(), CandidateDTO.class))
        .willReturn(dummyCandidateDTOs);

    final PagingResponse<CandidateDTO>
        expectedPagingResponse =
        new PagingResponse<>(dummyCandidateEntityPage.getTotalElements(), dummyCandidateDTOs);

    // When
    final PagingResponse<CandidateDTO>
        actualPagingResponse =
        candidateService.getCandidatesByFilterRequest(candidateFilterRequest);

    //Then
    assertEquals(actualPagingResponse, expectedPagingResponse);
    then(converterService).should(times(1))
        .convert(dummyCandidateEntityPage.getContent(), CandidateDTO.class);
    then(candidateRepository).should(times(1))
        .findByCandidateFilterRequest(name, email, phone, positions, pageRequest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void saveOrUpdateShouldThrowIAEWhenNullPassed() {
    // When
    candidateService.saveOrUpdate(null);
  }

  @Test
  public void saveOrUpdateShouldThrowDREAfterCatchingConstraintViolationException()
      throws DuplicateCandidateException {
    // Given
    given(converterService.convert(dummyCandidateDto, CandidateEntity.class))
        .willReturn(dummyCandidateEntity);

    given(candidateRepository.saveAndFlush(dummyCandidateEntity))
        .willThrow(ConstraintViolationException.class);

    expectedException.expect(DuplicateCandidateException.class);
    expectedException.expectMessage(CoreMatchers.endsWith(NAME));
    expectedException.expectCause(Matchers.isA(ConstraintViolationException.class));

    // When
    candidateService.saveOrUpdate(dummyCandidateDto);
    // Then
  }

  @Test
  public void saveOrUpdateShouldThrowDREAfterCatchingDataIntegrityViolationException()
      throws DuplicateCandidateException {
    // Given
    given(converterService.convert(dummyCandidateDto, CandidateEntity.class))
        .willReturn(dummyCandidateEntity);

    given(candidateRepository.saveAndFlush(dummyCandidateEntity))
        .willThrow(DataIntegrityViolationException.class);

    expectedException.expect(DuplicateCandidateException.class);
    expectedException.expectMessage(CoreMatchers.endsWith(NAME));
    expectedException.expectCause(Matchers.isA(DataIntegrityViolationException.class));

    // When
    candidateService.saveOrUpdate(dummyCandidateDto);
    // Then
  }

  @Test
  public void saveOrUpdateShouldSaveAProperCandidateDTO() {
    // Given
    given(converterService.convert(dummyCandidateDto, CandidateEntity.class))
        .willReturn(dummyCandidateEntity);

    given(candidateRepository.saveAndFlush(dummyCandidateEntity)).willReturn(dummyCandidateEntity);

    // When
    candidateService.saveOrUpdate(dummyCandidateDto);

    // Then
    then(candidateRepository).should(times(1)).saveAndFlush(dummyCandidateEntity);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCandidateDtoByEmailShouldThrowIllegalArgumentExceptionWhenParamEmailIsNull() {
    // Given

    // When
    this.candidateService.getCandidateDtoByEmail(null);

    // Then
  }

  @Test
  public void getCandidateDtoByEmailShouldReturnNullWhenCandidateEntityNotExistsWithThisPhone() {
    // Given
    given(this.candidateRepository.findByEmail(EMAIL)).willReturn(null);

    // When
    CandidateDTO actualCandidateDto = this.candidateService.getCandidateDtoByEmail(EMAIL);

    // Then
    assertThat(actualCandidateDto, nullValue());

    then(this.candidateRepository).should().findByEmail(EMAIL);
    verifyZeroInteractions(this.converterService);
  }

  @Test
  public void getCandidateDtoByEmailShouldReturnExistingCandidateDtoWhenCandidateEntityExistsWithThisPhone() {
    // Given
    given(this.candidateRepository.findByEmail(EMAIL)).willReturn(dummyCandidateEntity);
    given(this.converterService.convert(dummyCandidateEntity, CandidateDTO.class))
        .willReturn(dummyCandidateDto);

    // When
    CandidateDTO actualCandidateDto =  this.candidateService.getCandidateDtoByEmail(EMAIL);

    // Then
    assertThat(actualCandidateDto, notNullValue());
    assertThat(actualCandidateDto, equalTo(dummyCandidateDto));

    then(this.candidateRepository).should().findByEmail(EMAIL);
    then(this.converterService).should().convert(dummyCandidateEntity, CandidateDTO.class);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCandidateDtoByPhoneShouldThrowIllegalArgumentExceptionWhenParamPhoneIsNull() {
    // Given

    // When
    this.candidateService.getCandidateDtoByPhone(null);

    // Then
  }

  @Test
  public void getCandidateDtoByPhoneShouldReturnNullWhenCandidateEntityNotExistsWithThisPhone() {
    // Given
    given(this.candidateRepository.findByPhone(PHONE)).willReturn(null);

    // When
    CandidateDTO actualCandidateDto = this.candidateService.getCandidateDtoByPhone(PHONE);

    // Then
    assertThat(actualCandidateDto, nullValue());

    then(this.candidateRepository).should().findByPhone(PHONE);
    verifyZeroInteractions(this.converterService);
  }

  @Test
  public void getCandidateDtoByPhoneShouldReturnExistingCandidateDtoWhenCandidateEntityExistsWithThisPhone() {
    // Given
    given(this.candidateRepository.findByPhone(PHONE)).willReturn(dummyCandidateEntity);
    given(this.converterService.convert(dummyCandidateEntity, CandidateDTO.class))
        .willReturn(dummyCandidateDto);

    // When
    CandidateDTO actualCandidateDto =  this.candidateService.getCandidateDtoByPhone(PHONE);

    // Then
    assertThat(actualCandidateDto, notNullValue());
    assertThat(actualCandidateDto, equalTo(dummyCandidateDto));

    then(this.candidateRepository).should().findByPhone(PHONE);
    then(this.converterService).should().convert(dummyCandidateEntity, CandidateDTO.class);
  }
}