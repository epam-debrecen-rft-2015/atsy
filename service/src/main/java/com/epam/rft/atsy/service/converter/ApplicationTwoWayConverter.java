package com.epam.rft.atsy.service.converter;

import com.epam.rft.atsy.persistence.entities.ApplicationEntity;
import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.ChannelRepository;
import com.epam.rft.atsy.persistence.repositories.PositionRepository;
import com.epam.rft.atsy.service.domain.ApplicationDTO;

public class ApplicationTwoWayConverter
    extends AbstractTwoWayConverter<ApplicationEntity, ApplicationDTO> {

  private CandidateRepository candidateRepository;

  private PositionRepository positionRepository;

  private ChannelRepository channelRepository;

  public ApplicationTwoWayConverter(
      CandidateRepository candidateRepository,
      PositionRepository positionRepository,
      ChannelRepository channelRepository) {
    this.candidateRepository = candidateRepository;
    this.positionRepository = positionRepository;
    this.channelRepository = channelRepository;
  }

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
