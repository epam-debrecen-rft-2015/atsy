package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.service.domain.CandidateDTO;
import com.epam.rft.atsy.service.exception.DuplicateRecordException;
import com.epam.rft.atsy.service.request.FilterRequest;
import com.epam.rft.atsy.service.request.SearchOptions;
import com.epam.rft.atsy.service.request.SortingRequest;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CandidateServiceImplTest {
    @Mock
    private ModelMapper modelMapper;

    @Mock
    private CandidateRepository candidateRepository;

    @InjectMocks
    private CandidateServiceImpl candidateService;

    private static final Long ID = 1L;
    private static final String NAME = "John Doe";
    private static final String EMAIL = "john@doe.com";
    private static final String PHONE = "123456";
    private static final String REFERER = "Jane Doe";
    private static final Short LANGUAGE_SKILL = 5;
    private static final String DESCRIPTION = "Simply John Doe.";

    private static final String SORT_FIELD = "name";

    private static final CandidateEntity NOT_FOUND_CANDIDATE_ENTITY = null;
    private static final CandidateDTO NOT_FOUND_CANDIDATE_DTO = null;

    private CandidateEntity dummyCandidateEntity;

    private CandidateDTO dummyCandidateDto;

    private FilterRequest ascendingFilterRequest;

    private FilterRequest descendingFilterRequest;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        dummyCandidateEntity = CandidateEntity.builder().id(ID).name(NAME).email(EMAIL).phone(PHONE)
                                              .referer(REFERER).languageSkill(LANGUAGE_SKILL)
                                              .description(DESCRIPTION).build();

        dummyCandidateDto = CandidateDTO.builder().id(ID).name(NAME).email(EMAIL).phone(PHONE)
                                        .referer(REFERER).languageSkill(LANGUAGE_SKILL)
                                        .description(DESCRIPTION).build();

        ascendingFilterRequest = FilterRequest.builder().fieldName(SORT_FIELD).order(SortingRequest.Order.ASC)
                                              .searchOptions(new SearchOptions(NAME, EMAIL, PHONE)).build();

        descendingFilterRequest = FilterRequest.builder().fieldName(SORT_FIELD).order(SortingRequest.Order.DESC)
                                              .searchOptions(new SearchOptions(NAME, EMAIL, PHONE)).build();
    }

    @Test
    public void getCandidateShouldReturnNullWhenIdNotFound() {
        // Given
        given(candidateRepository.findOne(ID)).willReturn(NOT_FOUND_CANDIDATE_ENTITY);
        given(modelMapper.map(NOT_FOUND_CANDIDATE_ENTITY, CandidateDTO.class)).willReturn(NOT_FOUND_CANDIDATE_DTO);

        // When
        CandidateDTO candidate = candidateService.getCandidate(ID);

        // Then
        assertThat(candidate, nullValue());
    }

    @Test
    public void getCandidateShouldReturnProperDtoWhenIdExists() {
        // Given
        given(candidateRepository.findOne(ID)).willReturn(dummyCandidateEntity);
        given(modelMapper.map(dummyCandidateEntity, CandidateDTO.class)).willReturn(dummyCandidateDto);

        // When
        CandidateDTO candidate = candidateService.getCandidate(ID);

        // Then
        assertThat(candidate, equalTo(dummyCandidateDto));
    }

    @Test
    public void getAllCandidateShouldCallRepositoryWithAscendingSortWhenPassingAscendingFilterRequest() {
        // Given
        Sort ascendingSort = new Sort(Sort.Direction.ASC, SORT_FIELD);

        // When
        Collection<CandidateDTO> result = candidateService.getAllCandidate(ascendingFilterRequest);

        // Then
        ArgumentCaptor<String> nameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> emailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> phoneArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Sort> sortArgumentCaptor = ArgumentCaptor.forClass(Sort.class);

        verify(candidateRepository).findAllCandidatesByFilterRequest(
                nameArgumentCaptor.capture(), emailArgumentCaptor.capture(),
                phoneArgumentCaptor.capture(), sortArgumentCaptor.capture());

        assertThat(nameArgumentCaptor.getValue(), equalTo(NAME));
        assertThat(emailArgumentCaptor.getValue(), equalTo(EMAIL));
        assertThat(phoneArgumentCaptor.getValue(), equalTo(PHONE));
        assertThat(sortArgumentCaptor.getValue(), equalTo(ascendingSort));
    }

    @Test
    public void getAllCandidateShouldCallRepositoryWithDescendingSortWhenPassingDescendingFilterRequest() {
        // Given
        Sort descendingSort = new Sort(Sort.Direction.DESC, SORT_FIELD);

        // When
        Collection<CandidateDTO> result = candidateService.getAllCandidate(descendingFilterRequest);

        // Then
        ArgumentCaptor<String> nameArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> emailArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> phoneArgumentCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Sort> sortArgumentCaptor = ArgumentCaptor.forClass(Sort.class);

        verify(candidateRepository).findAllCandidatesByFilterRequest(
                nameArgumentCaptor.capture(), emailArgumentCaptor.capture(),
                phoneArgumentCaptor.capture(), sortArgumentCaptor.capture());

        assertThat(nameArgumentCaptor.getValue(), equalTo(NAME));
        assertThat(emailArgumentCaptor.getValue(), equalTo(EMAIL));
        assertThat(phoneArgumentCaptor.getValue(), equalTo(PHONE));
        assertThat(sortArgumentCaptor.getValue(), equalTo(descendingSort));
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveOrUpdateShouldThrowIAEWhenNullPassed() {
        // When
        candidateService.saveOrUpdate(null);
    }

    @Test
    public void saveOrUpdateShouldThrowDREAfterCatchingConstraintViolationException()
            throws DuplicateRecordException {
        // Given
        given(modelMapper.map(dummyCandidateDto, CandidateEntity.class)).willReturn(dummyCandidateEntity);

        given(candidateRepository.save(dummyCandidateEntity)).willThrow(ConstraintViolationException.class);

        expectedException.expect(DuplicateRecordException.class);
        expectedException.expectMessage(CoreMatchers.endsWith(NAME));
        expectedException.expectCause(Matchers.isA(ConstraintViolationException.class));

        // When
        candidateService.saveOrUpdate(dummyCandidateDto);
    }

    @Test
    public void saveOrUpdateShouldThrowDREAfterCatchingDataIntegrityViolationException()
            throws DuplicateRecordException {
        // Given
        given(modelMapper.map(dummyCandidateDto, CandidateEntity.class)).willReturn(dummyCandidateEntity);

        given(candidateRepository.save(dummyCandidateEntity)).willThrow(DataIntegrityViolationException.class);

        expectedException.expect(DuplicateRecordException.class);
        expectedException.expectMessage(CoreMatchers.endsWith(NAME));
        expectedException.expectCause(Matchers.isA(DataIntegrityViolationException.class));

        // When
        candidateService.saveOrUpdate(dummyCandidateDto);
    }

    @Test
    public void saveOrUpdateShouldSaveAProperCandidateDTO() {
        // Given
        given(modelMapper.map(dummyCandidateDto, CandidateEntity.class)).willReturn(dummyCandidateEntity);

        given(candidateRepository.save(dummyCandidateEntity)).willReturn(dummyCandidateEntity);

        // When
        candidateService.saveOrUpdate(dummyCandidateDto);

        // Then
        then(candidateRepository).should(times(1)).save(dummyCandidateEntity);
    }
}