package com.epam.rft.atsy.service;

import java.util.List;

public interface ConverterService {

  <S, T> T convert(S source, Class<T> targetType);

  <S, T> List<T> convert(List<S> sourceList, Class<T> targetType);

}
