package com.epam.rft.atsy.service.converter;

/**
 * Adapter class for {@link CustomConverter} used for saving the types of the source and target
 * objects.
 * @see AbstractOneWayConverter
 * @see AbstractTwoWayConverter
 */
public class ConverterAdapter {

  private Class sourceClass;
  private Class targetClass;
  private CustomConverter converter;

  /**
   * Constructs a ConverterAdapter with an encapsulated {@link CustomConverter} and types related to
   * it.
   * @param sourceClass the source type
   * @param targetClass the target type
   * @param converter the custom converter
   */
  public ConverterAdapter(Class targetClass, Class sourceClass, CustomConverter converter) {
    this.targetClass = targetClass;
    this.sourceClass = sourceClass;
    this.converter = converter;
  }

  public CustomConverter getConverter() {
    return converter;
  }

  public Class getTargetClass() {
    return targetClass;
  }

  public Class getSourceClass() {
    return sourceClass;
  }
}
