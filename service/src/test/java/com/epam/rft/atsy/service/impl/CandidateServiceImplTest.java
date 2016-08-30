package com.epam.rft.atsy.service.impl;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.exception.DuplicateCandidateException;
import com.epam.rft.atsy.service.request.FilterRequest;
import com.epam.rft.atsy.service.request.SearchOptions;
import com.epam.rft.atsy.service.request.SortingRequest;
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;

import java.util.Collection;

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
  private static final SortingRequest.Field SORT_FIELD = SortingRequest.Field.NAME;
  private static final CandidateEntity NOT_FOUND_CANDIDATE_ENTITY = null;
  private static final CandidateDTO NOT_FOUND_CANDIDATE_DTO = null;

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
  private FilterRequest ascendingFilterRequest;
  private FilterRequest descendingFilterRequest;
  private Sort ascendingSort;

  @Before
  public void setUp() {
    dummyCandidateEntity = CandidateEntity.builder().id(ID).name(NAME).email(EMAIL).phone(PHONE)
        .referer(REFERER).languageSkill(LANGUAGE_SKILL).description(DESCRIPTION).build();

    dummyApplicationEntity =
        ApplicationEntity.builder().id(APPLICATION_ID).candidateEntity(dummyCandidateEntity)
            .build();

    dummyCandidateDto = CandidateDTO.builder().id(ID).name(NAME).email(EMAIL).phone(PHONE)
        .referer(REFERER).languageSkill(LANGUAGE_SKILL).description(DESCRIPTION).build();

    ascendingFilterRequest =
        FilterRequest.builder().fieldName(SORT_FIELD).order(SortingRequest.Order.ASC)
            .searchOptions(new SearchOptions(NAME, EMAIL, PHONE)).build();

    descendingFilterRequest =
        FilterRequest.builder().fieldName(SORT_FIELD).order(SortingRequest.Order.DESC)
            .searchOptions(new SearchOptions(NAME, EMAIL, PHONE)).build();

    ascendingSort = new Sort(Sort.Direction.ASC, SORT_FIELD.toString());
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
    given(applicationsRepository.findOne(APPLICATION_ID))
        .willReturn(dummyApplicationEntity);
    given(converterService.convert(dummyCandidateEntity, CandidateDTO.class))
        .willReturn(dummyCandidateDto);

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
  public void getAllCandidatesShouldThrowIAEWhenFilterRequestIsNull() {
    // When
    candidateService.getAllCandidate(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getAllCandidatesShouldThrowIAEWhenSearchOptionsIsNull() {
    // Given
    FilterRequest defectedRequest =
        FilterRequest.builder().fieldName(SORT_FIELD).order(SortingRequest.Order.ASC)
            .searchOptions(null).build();

    // When
    candidateService.getAllCandidate(defectedRequest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getAllCandidatesShouldThrowIAEWhenFieldNameIsNull() {
    // Given
    FilterRequest defectedRequest =
        FilterRequest.builder().fieldName(null).order(SortingRequest.Order.ASC)
            .searchOptions(new SearchOptions(NAME, EMAIL, PHONE)).build();

    // When
    candidateService.getAllCandidate(defectedRequest);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getAllCandidatesShouldThrowIAEWhenOrderIsNull() {
    // Given
    FilterRequest defectedRequest =
        FilterRequest.builder().fieldName(SORT_FIELD).order(null)
            .searchOptions(new SearchOptions(NAME, EMAIL, PHONE)).build();

    // When
    candidateService.getAllCandidate(defectedRequest);
  }

  @Test
  public void getAllCandidatesShouldCallRepositoryWithEmptyStringAsNameWhenSearchOptionsNameIsNull() {
    // Given
    FilterRequest defectedRequest =
        FilterRequest.builder().fieldName(SORT_FIELD).order(SortingRequest.Order.ASC)
            .searchOptions(new SearchOptions(null, EMAIL, PHONE)).build();

    // When
    candidateService.getAllCandidate(defectedRequest);

    // Then
    verify(candidateRepository)
        .findAllByNameContainingAndEmailContainingAndPhoneContaining(StringUtils.EMPTY, EMAIL,
            PHONE, ascendingSort);
  }

  @Test
  public void getAllCandidatesShouldCallRepositoryWithEmptyStringAsEmailWhenSearchOptionsEmailIsNull() {
    // Given
    FilterRequest defectedRequest =
        FilterRequest.builder().fieldName(SORT_FIELD).order(SortingRequest.Order.ASC)
            .searchOptions(new SearchOptions(NAME, null, PHONE)).build();

    // When
    candidateService.getAllCandidate(defectedRequest);

    // Then
    verify(candidateRepository)
        .findAllByNameContainingAndEmailContainingAndPhoneContaining(NAME, StringUtils.EMPTY, PHONE,
            ascendingSort);
  }

  @Test
  public void getAllCandidatesShouldCallRepositoryWithEmptyStringAsPhoneWhenSearchOptionsPhoneIsNull() {
    // Given
    FilterRequest defectedRequest =
        FilterRequest.builder().fieldName(SORT_FIELD).order(SortingRequest.Order.ASC)
            .searchOptions(new SearchOptions(NAME, EMAIL, null)).build();

    // When
    candidateService.getAllCandidate(defectedRequest);

    // Then
    verify(candidateRepository)
        .findAllByNameContainingAndEmailContainingAndPhoneContaining(NAME, EMAIL, StringUtils.EMPTY,
            ascendingSort);
  }

  @Test
  public void getAllCandidateShouldCallRepositoryWithAscendingSortWhenPassingAscendingFilterRequest() {
    // When
    Collection<CandidateDTO> result = candidateService.getAllCandidate(ascendingFilterRequest);

    // Then
    verify(candidateRepository)
        .findAllByNameContainingAndEmailContainingAndPhoneContaining(NAME, EMAIL, PHONE,
            ascendingSort);
  }

  @Test
  public void getAllCandidateShouldCallRepositoryWithDescendingSortWhenPassingDescendingFilterRequest() {
    // Given
    Sort descendingSort = new Sort(Sort.Direction.DESC, SORT_FIELD.toString());

    // When
    Collection<CandidateDTO> result = candidateService.getAllCandidate(descendingFilterRequest);

    // Then
    verify(candidateRepository)
        .findAllByNameContainingAndEmailContainingAndPhoneContaining(NAME, EMAIL, PHONE,
            descendingSort);
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
}
