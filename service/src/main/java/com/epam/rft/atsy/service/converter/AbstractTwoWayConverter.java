package com.epam.rft.atsy.service.converter;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import org.springframework.core.GenericTypeResolver;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTwoWayConverter<E, D> implements TwoWayConverter<E, D> {

  @Override
  public List<ConverterAdapter> generate() {

    Class[]
        classes =
        GenericTypeResolver.resolveTypeArguments(this.getClass(), AbstractTwoWayConverter.class);

    Class entityClass = classes[0];
    Class dtoClass = classes[1];

    List<ConverterAdapter> converterAdapterList = new ArrayList<>();

    converterAdapterList.add(new ConverterAdapter(entityClass, dtoClass,
        new Converter<E, D>() {
          @Override
          public D convert(MappingContext<E, D> context) {
            return entityToDto(context.getSource());
          }
        })
    );

    converterAdapterList.add(new ConverterAdapter(dtoClass, entityClass,
        new Converter<D, E>() {
          @Override
          public E convert(MappingContext<D, E> context) {
            return dtoToEntity(context.getSource());
          }
        })
    );

    return converterAdapterList;
  }
}
