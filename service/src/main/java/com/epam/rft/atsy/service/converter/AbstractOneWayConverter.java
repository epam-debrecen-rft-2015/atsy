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
 * @param <F> the first type
 * @param <S> the second type
 */
public abstract class AbstractOneWayConverter<F, S> implements OneWayConverter<F, S> {

  protected Class[] parameterizedTypeClass;

  public AbstractOneWayConverter() {
    parameterizedTypeClass =
        GenericTypeResolver.resolveTypeArguments(this.getClass(), AbstractOneWayConverter.class);
  }

  @Override
  public List<ConverterAdapter> generate() {

    List<ConverterAdapter> converterAdapterList = new ArrayList<>();

    converterAdapterList
        .add(new ConverterAdapter(parameterizedTypeClass[0], parameterizedTypeClass[1],
            new CustomConverter<F, S>() {
              @Override
              public S convert(F source) {
                return firstTypeToSecondType(source);
              }
            })
        );

    return converterAdapterList;
  }
}
