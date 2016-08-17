package com.epam.rft.atsy.service.converter;

import java.util.List;

/**
 * An abstract implementation of the {@link TwoWayConverter} interface.
 * This abstract class is needed, because all {@code TwoWayConverters} will have the same
 * {@link BaseConverter#generate()} method.
 *
 * If you need to create a new TwoWayConverter, extend this class.
 * @param <F> the first type
 * @param <S> the second type
 */
public abstract class AbstractTwoWayConverter<F, S>
    extends AbstractOneWayConverter<F, S> implements TwoWayConverter<F, S> {

  public AbstractTwoWayConverter() {
    super();
  }

  @Override
  public List<ConverterAdapter> generate() {

    List<ConverterAdapter> converterAdapterList = super.generate();

    converterAdapterList
        .add(new ConverterAdapter(parameterizedTypeClass[1], parameterizedTypeClass[0],
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
