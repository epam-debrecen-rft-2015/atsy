package com.epam.rft.atsy.service.configuration;

import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.ChannelRepository;
import com.epam.rft.atsy.persistence.repositories.PositionRepository;
import com.epam.rft.atsy.persistence.repositories.StatesHistoryRepository;
import com.epam.rft.atsy.service.ApplicationsService;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.PositionService;
import com.epam.rft.atsy.service.converter.BaseConverter;
import com.epam.rft.atsy.service.converter.ConverterAdapter;
import com.epam.rft.atsy.service.converter.impl.ApplicationTwoWayConverter;
import com.epam.rft.atsy.service.converter.impl.CandidateApplicationOneWayConverter;
import com.epam.rft.atsy.service.converter.impl.CandidateOneWayConverter;
import com.epam.rft.atsy.service.converter.impl.StateFlowTwoWayConverter;
import com.epam.rft.atsy.service.converter.impl.StateHistoryTwoWayConverter;
import com.epam.rft.atsy.service.converter.impl.StateHistoryViewTwoWayConverter;
import com.epam.rft.atsy.service.impl.ModelMapperConverterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;

@Configuration
public class ConverterConfiguration {

  @Autowired
  private ConverterService converterService;

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private ChannelRepository channelRepository;

  @Autowired
  private StatesHistoryRepository statesHistoryRepository;

  @Autowired
  private ApplicationsService applicationsService;

  @Autowired
  private PositionService positionService;

  @PostConstruct
  public void converterAdapters() {
    List<BaseConverter> customConverters = Arrays.asList(
        new StateHistoryTwoWayConverter(converterService),
        new StateHistoryViewTwoWayConverter(converterService),
        new StateFlowTwoWayConverter(converterService),
        new ApplicationTwoWayConverter(candidateRepository, positionRepository, channelRepository),
        new CandidateApplicationOneWayConverter(statesHistoryRepository),
        new CandidateOneWayConverter(applicationsService, positionService)
    );

    List<ConverterAdapter> converterList = new ArrayList<>();
    for (BaseConverter customConverter : customConverters) {
      converterList.addAll(customConverter.generate());
    }

    ModelMapperConverterServiceImpl converterServiceImpl =
        (ModelMapperConverterServiceImpl) converterService;

    converterServiceImpl.setupCustomConverters(converterList);
  }

}
