package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.converter.ConverterAdapter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ModelMapperConverterServiceImpl implements ConverterService {

  private ModelMapper modelMapper = new ModelMapper();

  /**
   * Creates custom converters for {@code ModelMapper} using the {@code ConverterAdapters} given in
   * the {@code converterAdapters} parameter.
   * @param converterAdapters the list of ConverterAdapters
   */
  public void setupCustomConverters(List<ConverterAdapter> converterAdapters) {
    for (ConverterAdapter c : converterAdapters) {
      modelMapper.createTypeMap(c.getSourceClass(), c.getTargetClass())
          .setConverter(context -> c.getConverter().convert(context.getSource()));
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
