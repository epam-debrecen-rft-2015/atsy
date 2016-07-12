package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.*;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.ChannelRepository;
import com.epam.rft.atsy.persistence.repositories.PositionRepository;
import com.epam.rft.atsy.service.StatesService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationsServiceImplTest {

    private static final long APPLICATION_ID = 1L;
    private static final long CANDIDATE_ID = 1L;
    private static final String CANDIDATE_NAME = "Candidate A";
    private static final String CANDIDATE_EMAIL = "candidate.a@atsy.com";
    private static final String CANDIDATE_PHONE = "+36105555555";
    private static final String CANDIDATE_DESCRIPTION = "Elegáns, kicsit furi";
    private static final String CANDIDATE_REFERER = "google";
    private static final Short CANDIDATE_LANG_SKILL = 5;
    private static final long POSITION_ID = 1L;
    private static final String POSITION_NAME = "Fejlesztő";
    private static final long CHANNEL_ID = 8L;
    private static final String CHANNEL_NAME = "állásbőrze";
    private static final long SAVE_OR_UPDATE_RETURN_VALUE = 1L;

    private final ApplicationDTO applicationDTO= ApplicationDTO.builder().id(APPLICATION_ID).creationDate(new Date()).candidateId(CANDIDATE_ID).positionId(POSITION_ID).channelId(CHANNEL_ID).build();
    private final CandidateEntity candidateEntity = CandidateEntity.builder().id(CANDIDATE_ID).name(CANDIDATE_NAME).email(CANDIDATE_EMAIL).phone(CANDIDATE_PHONE).description(CANDIDATE_DESCRIPTION).referer(CANDIDATE_REFERER).languageSkill(CANDIDATE_LANG_SKILL).build();
    private final PositionEntity positionEntity = PositionEntity.builder().id(POSITION_ID).name(POSITION_NAME).build();
    private final ChannelEntity channelEntity = ChannelEntity.builder().id(CHANNEL_ID).name(CHANNEL_NAME).build();
    private final ApplicationEntity applicationEntity = ApplicationEntity.builder().id(APPLICATION_ID).creationDate(new Date()).candidateEntity(candidateEntity).positionEntity(positionEntity).channelEntity(channelEntity).build();
    private final StateDTO stateDTO = StateDTO.builder().stateType("newstate").stateIndex(0).build();


    @Mock
    private ModelMapper modelMapper;

    @Mock
    private StatesService statesService;

    @Mock
    private ApplicationsRepository applicationsRepository;

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private PositionRepository positionRepository;

    @Mock
    private ChannelRepository channelRepository;

    @InjectMocks
    private ApplicationsServiceImpl applicationsService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void saveOrUpdateShouldSaveAProperApplicationDTO() {

        // Given
        given(modelMapper.map(applicationDTO, ApplicationEntity.class)).willReturn(applicationEntity);
        given(candidateRepository.findOne(CANDIDATE_ID)).willReturn(candidateEntity);
        given(positionRepository.findOne(POSITION_ID)).willReturn(positionEntity);
        given(channelRepository.findOne(CHANNEL_ID)).willReturn(channelEntity);
        given(applicationsRepository.save(applicationEntity)).willReturn(applicationEntity);
        applicationEntity.setCandidateEntity(candidateRepository.findOne(CANDIDATE_ID));
        applicationEntity.setPositionEntity(positionRepository.findOne(POSITION_ID));
        applicationEntity.setChannelEntity(channelRepository.findOne(CHANNEL_ID));

        // When
        when(applicationsService.saveOrUpdate(applicationDTO)).thenReturn(SAVE_OR_UPDATE_RETURN_VALUE);
        when(applicationsRepository.save(applicationEntity)).thenReturn(applicationEntity);

        // Then
        assertNotNull(applicationEntity.getCreationDate());
        assertNotNull(applicationEntity.getCandidateEntity());
        assertNotNull(applicationEntity.getPositionEntity());
        assertNotNull(applicationEntity.getChannelEntity());

    }

    @Test(expected = NullPointerException.class)
    public void saveOrUpdateWithApplicationDTONullShouldThrowNullPointerException() {
        final ApplicationDTO applicationDTO= null;

        // Given
        given(modelMapper.map(applicationDTO, ApplicationEntity.class)).willReturn(applicationEntity);
        given(candidateRepository.findOne(CANDIDATE_ID)).willReturn(candidateEntity);
        given(positionRepository.findOne(POSITION_ID)).willReturn(positionEntity);
        given(channelRepository.findOne(CHANNEL_ID)).willReturn(channelEntity);
        given(applicationsRepository.save(applicationEntity)).willReturn(applicationEntity);
        applicationEntity.setCandidateEntity(candidateRepository.findOne(CANDIDATE_ID));
        applicationEntity.setPositionEntity(positionRepository.findOne(POSITION_ID));
        applicationEntity.setChannelEntity(channelRepository.findOne(CHANNEL_ID));

        // When
        when(applicationsService.saveOrUpdate(applicationDTO)).thenThrow(NullPointerException.class);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveOrUpdateWithCandidateDTONullShouldThrowConstraintViolationException() {
        final ApplicationEntity applicationEntity = ApplicationEntity.builder().id(APPLICATION_ID).creationDate(new Date()).candidateEntity(null).positionEntity(positionEntity).channelEntity(channelEntity).build();

        // Given
        given(modelMapper.map(applicationDTO, ApplicationEntity.class)).willReturn(applicationEntity);
        given(candidateRepository.findOne(CANDIDATE_ID)).willReturn(null);
        given(positionRepository.findOne(POSITION_ID)).willReturn(positionEntity);
        given(channelRepository.findOne(CHANNEL_ID)).willReturn(channelEntity);
        given(applicationsRepository.save(applicationEntity)).willThrow(ConstraintViolationException.class);
        applicationEntity.setCandidateEntity(candidateRepository.findOne(CANDIDATE_ID));
        applicationEntity.setPositionEntity(positionRepository.findOne(POSITION_ID));
        applicationEntity.setChannelEntity(channelRepository.findOne(CHANNEL_ID));

        // When
        applicationsRepository.save(applicationEntity);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveOrUpdateWithPositionEntityNullShouldThrowConstraintViolationException() {
        final ApplicationEntity applicationEntity = ApplicationEntity.builder().id(APPLICATION_ID).creationDate(new Date()).candidateEntity(candidateEntity).positionEntity(null).channelEntity(channelEntity).build();

        // Given
        given(modelMapper.map(applicationDTO, ApplicationEntity.class)).willReturn(applicationEntity);
        given(candidateRepository.findOne(CANDIDATE_ID)).willReturn(candidateEntity);
        given(positionRepository.findOne(POSITION_ID)).willReturn(null);
        given(channelRepository.findOne(CHANNEL_ID)).willReturn(channelEntity);
        given(applicationsRepository.save(applicationEntity)).willThrow(ConstraintViolationException.class);
        applicationEntity.setCandidateEntity(candidateRepository.findOne(CANDIDATE_ID));
        applicationEntity.setPositionEntity(positionRepository.findOne(POSITION_ID));
        applicationEntity.setChannelEntity(channelRepository.findOne(CHANNEL_ID));

        // When
        applicationsRepository.save(applicationEntity);
    }

    @Test(expected = ConstraintViolationException.class)
    public void saveOrUpdateWithChannelEntityNullShouldThrowConstraintViolationException() {
        final ApplicationEntity applicationEntity = ApplicationEntity.builder().id(APPLICATION_ID).creationDate(new Date()).candidateEntity(candidateEntity).positionEntity(positionEntity).channelEntity(null).build();

        // Given
        given(modelMapper.map(applicationDTO, ApplicationEntity.class)).willReturn(applicationEntity);
        given(candidateRepository.findOne(CANDIDATE_ID)).willReturn(candidateEntity);
        given(positionRepository.findOne(POSITION_ID)).willReturn(positionEntity);
        given(channelRepository.findOne(CHANNEL_ID)).willReturn(null);
        given(applicationsRepository.save(applicationEntity)).willThrow(ConstraintViolationException.class);
        applicationEntity.setCandidateEntity(candidateRepository.findOne(CANDIDATE_ID));
        applicationEntity.setPositionEntity(positionRepository.findOne(POSITION_ID));
        applicationEntity.setChannelEntity(channelRepository.findOne(CHANNEL_ID));

        // When
        applicationsRepository.save(applicationEntity);
    }

    @Test(expected = NullPointerException.class)
    public void saveOrUpdateWithApplicationEntityNullShouldThrowIllegalArgumentException() {
        final ApplicationEntity applicationEntity = null;

        // Given
        given(modelMapper.map(applicationDTO, ApplicationEntity.class)).willReturn(applicationEntity);
        given(candidateRepository.findOne(CANDIDATE_ID)).willReturn(candidateEntity);
        given(positionRepository.findOne(POSITION_ID)).willReturn(positionEntity);
        given(channelRepository.findOne(CHANNEL_ID)).willReturn(channelEntity);
        given(applicationsRepository.save(applicationEntity)).willReturn(applicationEntity);
        applicationEntity.setCandidateEntity(candidateRepository.findOne(CANDIDATE_ID));
        applicationEntity.setPositionEntity(positionRepository.findOne(POSITION_ID));
        applicationEntity.setChannelEntity(channelRepository.findOne(CHANNEL_ID));

        // When
        applicationsRepository.save(applicationEntity);
    }


    @Test
    public void saveApplicationShouldSaveAProperApplicationDTOAndStateDTO() {

        // Given
        given(modelMapper.map(applicationDTO, ApplicationEntity.class)).willReturn(applicationEntity);
        given(candidateRepository.findOne(CANDIDATE_ID)).willReturn(candidateEntity);
        given(positionRepository.findOne(POSITION_ID)).willReturn(positionEntity);
        given(channelRepository.findOne(CHANNEL_ID)).willReturn(channelEntity);
        given(applicationsRepository.save(applicationEntity)).willReturn(applicationEntity);

        // When
        Long applicationId = applicationsService.saveOrUpdate(applicationDTO);
        applicationsService.saveApplicaton(applicationDTO,stateDTO);

        // Then
        assertEquals(applicationId,(Long)SAVE_OR_UPDATE_RETURN_VALUE);
        verify(statesService).saveState(stateDTO,applicationId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveApplicationWithApplicationDTONullShouldThrowIllegalArgumentException() {
        final ApplicationDTO applicationDTO = null;

        // Given
        given(modelMapper.map(applicationDTO, ApplicationEntity.class)).willThrow(IllegalArgumentException.class);
        given(candidateRepository.findOne(CANDIDATE_ID)).willReturn(candidateEntity);
        given(positionRepository.findOne(POSITION_ID)).willReturn(positionEntity);
        given(channelRepository.findOne(CHANNEL_ID)).willReturn(channelEntity);
        given(applicationsRepository.save(applicationEntity)).willReturn(applicationEntity);

        // When
        Long applicationId = applicationsService.saveOrUpdate(applicationDTO);
        applicationsService.saveApplicaton(applicationDTO,stateDTO);

        // Then
        assertEquals(applicationId,(Long)SAVE_OR_UPDATE_RETURN_VALUE);
        verify(statesService).saveState(stateDTO,applicationId);
    }

    @Test
    public void saveApplicationWithStateDTONullShouldThrowIllegalArgumentException() {
        final StateDTO stateDTO = null;

        // Given
        given(modelMapper.map(applicationDTO, ApplicationEntity.class)).willReturn(applicationEntity);
        given(candidateRepository.findOne(CANDIDATE_ID)).willReturn(candidateEntity);
        given(positionRepository.findOne(POSITION_ID)).willReturn(positionEntity);
        given(channelRepository.findOne(CHANNEL_ID)).willReturn(channelEntity);
        given(applicationsRepository.save(applicationEntity)).willReturn(applicationEntity);
        given(modelMapper.map(stateDTO, StateEntity.class)).willThrow(IllegalArgumentException.class);

        // When
        Long applicationId = applicationsService.saveOrUpdate(applicationDTO);
        applicationsService.saveApplicaton(applicationDTO,stateDTO);

        // Then
        assertEquals(applicationId,(Long)SAVE_OR_UPDATE_RETURN_VALUE);
        verify(statesService).saveState(stateDTO,applicationId);
    }
}