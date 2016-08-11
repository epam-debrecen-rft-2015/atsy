package com.epam.rft.atsy.service.converter;

import org.springframework.core.GenericTypeResolver;

import java.util.ArrayList;
import java.util.List;

/**
 * An abstract implementation of {@link TwoWayConverter}. This abstract class is needed, because all
 * {@code TwoWayConverters} will have the same {@code generate()} method.
 * @param <F> the first type
 * @param <S> the second type
 */
public abstract class AbstractTwoWayConverter<F, S> implements TwoWayConverter<F, S> {

  @Override
  public List<ConverterAdapter> generate() {

    Class[]
        parameterTypeClasses =
        GenericTypeResolver.resolveTypeArguments(this.getClass(), AbstractTwoWayConverter.class);

    Class entityClass = parameterTypeClasses[0];
    Class dtoClass = parameterTypeClasses[1];

    List<ConverterAdapter> converterAdapterList = new ArrayList<>();

    converterAdapterList.add(new ConverterAdapter(entityClass, dtoClass,
        new CustomConverter<F, S>() {
          @Override
          public S convert(F source) {
            return firstTypeToSecondType(source);
          }
        })
    );

    converterAdapterList.add(new ConverterAdapter(dtoClass, entityClass,
        new CustomConverter<S, F>() {
          @Override
          public F convert(S source) {
            return secondTypeToFirstType(source);
          }
        })
    );

    return converterAdapterList;
  }
}
