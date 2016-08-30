package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.converter.ConverterAdapter;
import org.modelmapper.ModelMapper;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the {@code ConverterService} interface, using ModelMapper.
 */
public class ModelMapperConverterServiceImpl implements ConverterService {

  private ModelMapper modelMapper;

  /**
   * Constructs a new ModelMapperConverterServiceImpl.
   * @param modelMapper a ModelMapper instance, which will be used for converting
   */
  public ModelMapperConverterServiceImpl(ModelMapper modelMapper) {
    Assert.notNull(modelMapper);
    this.modelMapper = modelMapper;
  }

  /**
   * Creates custom converters for {@code ModelMapper} using the {@code ConverterAdapters} given in
   * the {@code converterAdapters} parameter.
   * @param converterAdapters the list of ConverterAdapters
   */
  public void setupCustomConverters(List<ConverterAdapter> converterAdapters) {
    Assert.notNull(converterAdapters);
    for (ConverterAdapter c : converterAdapters) {
      modelMapper.createTypeMap(c.getSourceClass(), c.getTargetClass())
          .setConverter(context -> c.getConverter().convert(context.getSource()));
    }
  }

  @Override
  public <S, T> T convert(S source, Class<T> targetType) {
    Assert.notNull(source);
    Assert.notNull(targetType);
    return modelMapper.map(source, targetType);
  }

  @Override
  public <S, T> List<T> convert(List<S> sourceList, Class<T> targetType) {
    Assert.notNull(sourceList);
    Assert.notNull(targetType);
    List<T> targetList = new ArrayList<>();
    for (S s : sourceList) {
      targetList.add(modelMapper.map(s, targetType));
    }
    return targetList;
  }

}
