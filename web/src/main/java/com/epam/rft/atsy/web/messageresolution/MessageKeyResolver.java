package com.epam.rft.atsy.web.messageresolution;

/**
 * Interface for custom message key resolving. Implementations should be able to resolve message
 * keys according to the current locale.
 */
public interface MessageKeyResolver {
  /**
   * Resolves the specified message key and interpolates the passed substitution objects into the
   * message.
   *
   * Note that although {@code MessageResolutionFailedException} is an unchecked exception, it is
   * crucial to be aware of the fact that it will be thrown in case of a resolution failure.
   * @param messageKey the message key to resolve
   * @param substitutions the substitution objects to interpolate
   * @return the resolved message
   * @throws MessageResolutionFailedException if the message resolution fails
   */
  String resolveMessage(String messageKey, Object... substitutions)
      throws MessageResolutionFailedException;
}
