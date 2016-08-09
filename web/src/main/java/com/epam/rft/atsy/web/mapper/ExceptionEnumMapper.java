package com.epam.rft.atsy.web.mapper;


public interface ExceptionEnumMapper<T extends Enum> {

  T mapByException(Exception e) throws IllegalArgumentException;

  String getValueNameByException(Exception e) throws IllegalArgumentException;
}
