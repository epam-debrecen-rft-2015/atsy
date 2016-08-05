package com.epam.rft.atsy.service.configuration;

import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.converter.ApplicationTwoWayConverter;
import com.epam.rft.atsy.service.converter.StateFlowTwoWayConverter;
import com.epam.rft.atsy.service.converter.StateHistoryTwoWayConverter;
import com.epam.rft.atsy.service.converter.StateHistoryViewTwoWayConverter;
import com.epam.rft.atsy.service.converter.TwoWayConverter;
import com.epam.rft.atsy.service.impl.ModelMapperConverterServiceImpl;
import org.modelmapper.Converter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
public class ConverterConfiguration {

  @Bean
  ConverterService converterService() {
    return new ModelMapperConverterServiceImpl(converters());
  }

  List<Converter> converters() {
    List<TwoWayConverter> twoWayConverters = Arrays.asList(
        new StateHistoryTwoWayConverter(),
        new StateHistoryViewTwoWayConverter(),
        new StateFlowTwoWayConverter(),
        new ApplicationTwoWayConverter()
    );

    return twoWayConverters.stream()
        .map(TwoWayConverter::generate)
        .flatMap(List::stream).collect(Collectors.toList());
  }

}
