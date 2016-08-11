package com.epam.rft.atsy.service.configuration;

import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.ChannelRepository;
import com.epam.rft.atsy.persistence.repositories.PositionRepository;
import com.epam.rft.atsy.persistence.repositories.StatesHistoryRepository;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.converter.ApplicationTwoWayConverter;
import com.epam.rft.atsy.service.converter.CandidateApplicationOneWayConverter;
import com.epam.rft.atsy.service.converter.ConverterAdapter;
import com.epam.rft.atsy.service.converter.BaseConverter;
import com.epam.rft.atsy.service.converter.StateFlowTwoWayConverter;
import com.epam.rft.atsy.service.converter.StateHistoryTwoWayConverter;
import com.epam.rft.atsy.service.converter.StateHistoryViewTwoWayConverter;
import com.epam.rft.atsy.service.impl.ModelMapperConverterServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
public class ConverterConfiguration {

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PositionRepository positionRepository;

  @Autowired
  private ChannelRepository channelRepository;

  @Autowired
  private StatesHistoryRepository statesHistoryRepository;

  @Bean
  public ConverterService converterService() {
    ModelMapperConverterServiceImpl
        modelMapperConverterService =
        new ModelMapperConverterServiceImpl();
    modelMapperConverterService
        .setupCustomConverters(converterAdapters(modelMapperConverterService));
    return modelMapperConverterService;
  }

  private List<ConverterAdapter> converterAdapters(ConverterService converterService) {
    List<BaseConverter> customConverters = Arrays.asList(
        new StateHistoryTwoWayConverter(converterService),
        new StateHistoryViewTwoWayConverter(converterService),
        new StateFlowTwoWayConverter(converterService),
        new ApplicationTwoWayConverter(candidateRepository, positionRepository, channelRepository),
        new CandidateApplicationOneWayConverter(statesHistoryRepository)
    );

    List<ConverterAdapter> converterList = new ArrayList<>();
    for (BaseConverter customConverter : customConverters) {
      converterList.addAll(customConverter.generate());
    }
    return converterList;

  }

}
