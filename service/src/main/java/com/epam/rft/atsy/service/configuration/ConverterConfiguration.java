package com.epam.rft.atsy.service.configuration;

import com.epam.rft.atsy.persistence.repositories.CandidateRepository;
import com.epam.rft.atsy.persistence.repositories.ChannelRepository;
import com.epam.rft.atsy.persistence.repositories.PositionRepository;
import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.converter.ApplicationTwoWayConverter;
import com.epam.rft.atsy.service.converter.ConverterAdapter;
import com.epam.rft.atsy.service.converter.StateFlowTwoWayConverter;
import com.epam.rft.atsy.service.converter.StateHistoryTwoWayConverter;
import com.epam.rft.atsy.service.converter.StateHistoryViewTwoWayConverter;
import com.epam.rft.atsy.service.converter.TwoWayConverter;
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

  @Bean
  ConverterService converterService() {
    return new ModelMapperConverterServiceImpl(converterAdapters());
  }

  private List<ConverterAdapter> converterAdapters() {
    List<TwoWayConverter> twoWayConverters = Arrays.asList(
        new StateHistoryTwoWayConverter(),
        new StateHistoryViewTwoWayConverter(),
        new StateFlowTwoWayConverter(),
        new ApplicationTwoWayConverter(candidateRepository, positionRepository, channelRepository)
    );

    List<ConverterAdapter> converterList = new ArrayList<>();
    for (TwoWayConverter twoWayConverter : twoWayConverters) {
      converterList.addAll(twoWayConverter.generate());
    }
    return converterList;

//    return twoWayConverters.stream()
//        .map(TwoWayConverter::generate)
//        .flatMap(List::stream)
//        .collect(Collectors.toCollection(ArrayList::new));
  }

}
