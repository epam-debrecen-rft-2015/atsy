package com.epam.rft.atsy.web.mapper;

/**
 * This interface is responsible for mapping the validation exceptions to their corresponding
 * messages.
 *
 * However, the mapping is only one sided, the exception can be mapped to its message but
 * not the other way around.
 */
@FunctionalInterface
public interface RuleValidationExceptionMapper {

  /**
   * Maps the given exception to its corresponding exception message.
   * @param e the exception whose exception message should be returned
   * @return the corresponding message of the given exception
   * @throws IllegalArgumentException if the given exception is null or is not a validation
   * exception.
   */
  String getMessageKeyByException(Exception e) throws IllegalArgumentException;

}
