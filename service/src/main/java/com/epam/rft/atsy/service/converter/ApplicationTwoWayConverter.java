package com.epam.rft.atsy.service.converter;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.ChannelRepository;
import com.epam.rft.atsy.persistence.repositories.PositionRepository;
import com.epam.rft.atsy.service.domain.ApplicationDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class ApplicationTwoWayConverter
    extends AbstractTwoWayConverter<ApplicationEntity, ApplicationDTO> {

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private ChannelRepository channelRepository;

  @Override
  public ApplicationDTO entityToDto(ApplicationEntity source) {
    return ApplicationDTO.builder()
        .id(source.getId())
        .creationDate(source.getCreationDate())
        .candidateId(source.getCandidateEntity().getId())
        .positionId(source.getPositionEntity().getId())
        .channelId(source.getChannelEntity().getId())
        .build();
  }

  @Override
  public ApplicationEntity dtoToEntity(ApplicationDTO source) {
    return ApplicationEntity.builder()
        .id(source.getId())
        .creationDate(source.getCreationDate())
        .candidateEntity(candidateRepository.findOne(source.getCandidateId()))
        .positionEntity(positionRepository.findOne(source.getPositionId()))
        .channelEntity(channelRepository.findOne(source.getChannelId()))
        .build();
  }
}
