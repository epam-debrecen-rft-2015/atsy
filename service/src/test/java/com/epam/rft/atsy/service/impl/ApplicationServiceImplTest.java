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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;

import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.BDDMockito.given;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ApplicationServiceImplTest {

    public static final long APPLICATION_ID = 1L;
    public static final long CANDIDATE_ID = 1L;
    public static final String CANDIDATE_NAME = "Candidate A";
    public static final String CANDIDATE_EMAIL = "candidate.a@atsy.com";
    public static final String CANDIDATE_PHONE = "+36105555555";
    public static final String CANDIDATE_DESCRIPTION = "Elegáns, kicsit furi";
    public static final String CANDIDATE_REFERER = "google";
    public static final Short CANDIDATE_LANG_SKILL = 5;
    public static final long POSITION_ID = 1L;
    public static final String POSITION_NAME = "Fejlesztő";
    public static final long CHANNEL_ID = 8L;
    public static final String CHANNEL_NAME = "állásbőrze";
    public static final long SAVE_OR_UPDATE_RETURN_VALUE = 1L;

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

    @Test
    public void saveOrUpdateShouldSaveAProperApplicationDTO() {
        final ApplicationDTO applicationDTO= ApplicationDTO.builder().id(APPLICATION_ID).creationDate(new Date()).candidateId(CANDIDATE_ID).positionId(POSITION_ID).channelId(CHANNEL_ID).build();
        final CandidateEntity candidateEntity = CandidateEntity.builder().id(CANDIDATE_ID).name(CANDIDATE_NAME).email(CANDIDATE_EMAIL).phone(CANDIDATE_PHONE).description(CANDIDATE_DESCRIPTION).referer(CANDIDATE_REFERER).languageSkill(CANDIDATE_LANG_SKILL).build();
        final PositionEntity positionEntity = PositionEntity.builder().id(POSITION_ID).name(POSITION_NAME).build();
        final ChannelEntity channelEntity = ChannelEntity.builder().id(CHANNEL_ID).name(CHANNEL_NAME).build();
        final ApplicationEntity applicationEntity = ApplicationEntity.builder().id(APPLICATION_ID).creationDate(new Date()).candidateEntity(candidateEntity).positionEntity(positionEntity).channelEntity(channelEntity).build();

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
}