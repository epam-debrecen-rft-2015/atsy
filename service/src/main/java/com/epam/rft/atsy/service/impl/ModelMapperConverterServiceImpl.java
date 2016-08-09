package com.epam.rft.atsy.service.impl;

import com.epam.rft.atsy.service.ConverterService;
import com.epam.rft.atsy.service.converter.ConverterAdapter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class ModelMapperConverterServiceImpl implements ConverterService {

  private ModelMapper modelMapper = new ModelMapper();

  public ModelMapperConverterServiceImpl(List<ConverterAdapter> converterAdapters) {

    System.out.println("-------------------------" + modelMapper + "--------------");
    for (ConverterAdapter c : converterAdapters) {
//      System.out
//          .println("+++++++++++++++++++++ ModelMapper:" + modelMapper + "++++++++++++++++++++");
//      System.out.println("+++++++++++++++++++++ Converter:" + c + "++++++++++++++++++++");
//      Class[] typeArguments = TypeResolver.resolveArguments(c.getClass(), Converter.class);
//      System.out.println(typeArguments);

      //modelMapper.addConverter(c);
      modelMapper.createTypeMap(c.getEntityClass(), c.getDtoClass()).setConverter(c.getConverter());
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
