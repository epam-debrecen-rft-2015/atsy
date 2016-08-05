package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.service.ConverterService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ModelMapperConverterServiceImpl implements ConverterService {

  private ModelMapper modelMapper = new ModelMapper();

  public ModelMapperConverterServiceImpl(List<Converter>... converters) {

    for (List<Converter> cList : converters) {
      for (Converter c : cList) {
        modelMapper.addConverter(c);
      }
    }
  }

  @Override
  public <S, T> T convert(S source, Class<T> targetType) {
    return modelMapper.map(source, targetType);
  }

  @Override
  public <S, T> List<T> convert(List<S> sourceList, Class<T> targetType) {
    List<T> targetList = new ArrayList<>();
    for (S s : sourceList) {
      targetList.add(modelMapper.map(s, targetType));
    }
    return targetList;
  }

}
