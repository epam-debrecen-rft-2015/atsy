package com.epam.rft.atsy.web.messageresolution;

/**
 * Interface for custom message key resolving. Implementations should be able to resolve message
 * keys according to the current locale.
 */
public interface MessageKeyResolver {
  /**
   * Resolves the specified message key and interpolates the passed substitution objects into the
   * message.
   * @param messageKey the message key to resolve
   * @param substitutions the substitution objects to interpolate
   * @return the resolved message
   * @throws MessageResolutionFailedException if the message resolution fails
   */
  String resolveMessage(String messageKey, Object... substitutions)
      throws MessageResolutionFailedException;

  /**
   * Resolves the specified message the same way as {@link #resolveMessage(String, Object...)} but
   * instead of throwing {@code MessageResolutionFailedException} upon failure it returns the
   * <b>message key</b>.
   * @param messageKey the message key to resolve
   * @param substitutions the substitution objects to interpolate
   * @return the resolved message or the message key
   */
  String resolveMessageOrDefault(String messageKey, Object... substitutions);
}
