package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.entities.CandidateEntity;
import com.epam.rft.atsy.persistence.entities.ChannelEntity;
import com.epam.rft.atsy.persistence.entities.PositionEntity;
import com.epam.rft.atsy.persistence.repositories.ApplicationsRepository;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.ChannelRepository;
import com.epam.rft.atsy.persistence.repositories.PositionRepository;
import com.epam.rft.atsy.service.StatesService;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import com.epam.rft.atsy.service.domain.ChannelDTO;
import com.epam.rft.atsy.service.domain.PositionDTO;
import com.epam.rft.atsy.service.domain.states.StateDTO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationsServiceImplTest {

    private static final Long APPLICATION_ID = 1L;
    private static final Long CANDIDATE_ID = 1L;
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
        final ApplicationEntity applicationEntity = new ApplicationEntity();

        given(modelMapper.map(applicationDTO, ApplicationEntity.class)).willReturn(applicationEntity);
        given(candidateRepository.findOne(CANDIDATE_ID)).willReturn(candidateEntity);
        given(positionRepository.findOne(POSITION_ID)).willReturn(positionEntity);
        given(channelRepository.findOne(CHANNEL_ID)).willReturn(channelEntity);
        given(applicationsRepository.save(applicationEntity)).willReturn(this.applicationEntity);

        // When
        Long result = applicationsService.saveOrUpdate(applicationDTO);

        // Then
        assertNotNull(result);
        assertEquals(APPLICATION_ID, result);

        assertNotNull(applicationEntity.getCandidateEntity());
        assertEquals(candidateEntity, applicationEntity.getCandidateEntity());

        assertNotNull(applicationEntity.getPositionEntity());
        assertEquals(positionEntity, applicationEntity.getPositionEntity());

        assertNotNull(applicationEntity.getChannelEntity());
        assertEquals(channelEntity, applicationEntity.getChannelEntity());

        then(modelMapper).should().map(applicationDTO, ApplicationEntity.class);
        then(candidateRepository).should().findOne(CANDIDATE_ID);
        then(positionRepository).should().findOne(POSITION_ID);
        then(channelRepository).should().findOne(CHANNEL_ID);
        then(applicationsRepository).should().save(applicationEntity);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveOrUpdateWithApplicationDTONullShouldThrowIllegalArgumentException() {

        // When
        Long result = applicationsService.saveOrUpdate(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveOrUpdateWithApplicationDTOsCandidateIDNullShouldThrowIllegalArgumentException() {
        final ApplicationDTO applicationDTO= ApplicationDTO.builder().id(APPLICATION_ID).creationDate(new Date()).candidateId(null).positionId(POSITION_ID).channelId(CHANNEL_ID).build();

        // When
        Long result = applicationsService.saveOrUpdate(applicationDTO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveOrUpdateWithApplicationDTOsPositionIDNullShouldThrowIllegalArgumentException() {
        final ApplicationDTO applicationDTO= ApplicationDTO.builder().id(APPLICATION_ID).creationDate(new Date()).candidateId(CANDIDATE_ID).positionId(null).channelId(CHANNEL_ID).build();

        // When
        Long result = applicationsService.saveOrUpdate(applicationDTO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveOrUpdateWithApplicationDTOsChannelIDNullShouldThrowIllegalArgumentException() {
        final ApplicationDTO applicationDTO= ApplicationDTO.builder().id(APPLICATION_ID).creationDate(new Date()).candidateId(CANDIDATE_ID).positionId(POSITION_ID).channelId(null).build();

        // When
        Long result = applicationsService.saveOrUpdate(applicationDTO);
    }

    @Test///////////////////////////////////////////////////////////////////////////////
    public void saveApplicationShouldSaveAProperApplicationDTOAndStateDTO() {
        final StateDTO stateDTO = StateDTO.builder().channel(new ChannelDTO(8L,null)).candidateId(1L).position(new PositionDTO(1L,null)).description("sss").stateType("newstate").stateIndex(0).build();

        // Given
        given(modelMapper.map(applicationDTO, ApplicationEntity.class)).willReturn(applicationEntity);
        given(candidateRepository.findOne(CANDIDATE_ID)).willReturn(candidateEntity);
        given(positionRepository.findOne(POSITION_ID)).willReturn(positionEntity);
        given(channelRepository.findOne(CHANNEL_ID)).willReturn(channelEntity);
        given(applicationsRepository.save(applicationEntity)).willReturn(this.applicationEntity);
        // When
        Long result = applicationsService.saveApplicaton(applicationDTO,stateDTO);

        // Then
        assertNotNull(result);
        assertEquals(APPLICATION_ID, result);
        
        then(statesService).should().saveState(stateDTO,APPLICATION_ID);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveApplicationWithApplicationDTONullShouldThrowIllegalArgumentException() {

        // When
        Long result = applicationsService.saveApplicaton(applicationDTO,null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void saveApplicationWithStateDTONullShouldThrowIllegalArgumentException() {

        // When
        Long result = applicationsService.saveApplicaton(applicationDTO,null);
    }
}