package com.epam.rft.atsy.service.converter;

import org.springframework.core.GenericTypeResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract implementation of the {@link OneWayConverter} interface.
 * This abstract class is needed, because all {@code OneWayConverters} will have the same
 * {@link BaseConverter#generate()} method.
 *
 * If you need to create a new OneWayConverter, extend this class.
 * @param <S> the source type
 * @param <T> the target type
 */
public abstract class AbstractOneWayConverter<S, T> implements OneWayConverter<S, T> {

  @Override
  public List<ConverterAdapter> generate() {
    Class[]
        parameterizedTypeClasses =
        GenericTypeResolver.resolveTypeArguments(this.getClass(), AbstractOneWayConverter.class);

    Class entityClass = parameterizedTypeClasses[0];
    Class dtoClass = parameterizedTypeClasses[1];

    List<ConverterAdapter> converterAdapterList = new ArrayList<>();

    converterAdapterList.add(new ConverterAdapter(entityClass, dtoClass,
        new CustomConverter<S, T>() {
          @Override
          public T convert(S source) {
            return sourceTypeToTargetType(source);
          }
        })
    );

    return converterAdapterList;
  }
}
