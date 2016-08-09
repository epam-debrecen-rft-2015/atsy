package com.epam.rft.atsy.service.converter;

import org.modelmapper.Converter;

public class ConverterAdapter {

  private Class entityClass;
  private Class dtoClass;
  private Converter converter;

  public ConverterAdapter(Class entityClass, Class dtoClass, Converter converter) {
    this.entityClass = entityClass;
    this.dtoClass = dtoClass;
    this.converter = converter;
  }

  public Converter getConverter() {
    return converter;
  }

  public Class getEntityClass() {
    return entityClass;
  }

  public Class getDtoClass() {
    return dtoClass;
  }
}
