package com.epam.rft.atsy.web.messageresolution;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class MessageKeyResolverImplTest {
  private static final String NON_EXISTENT_MESSAGE_KEY = "not.found";

  private static final String EXAMPLE_MESSAGE_KEY = "happy.birthday";

  private static final String EXAMPLE_MESSAGE = "Happy Birthday!";

  private static final String EXAMPLE_WITH_NAME_MESSAGE_KEY = "happy.birthday.with.name";

  private static final String EXAMPLE_WITH_NAME_MESSAGE = "Happy Birthday, {0}!";

  private static final String NAME = "Snowball";

  private static final String INTERPOLATED_EXAMPLE_MESSAGE = "Happy Birthday, Snowball!";

  @Mock
  private MessageSource messageSource;

  @InjectMocks
  private MessageKeyResolverImpl messageKeyResolver;

  @Test(expected = MessageResolutionFailedException.class)
  public void resolveMessageShouldThrowMessageResolutionFailedExceptionWhenMessageKeyIsNull()
      throws MessageResolutionFailedException {
    // Given
    given(messageSource.getMessage(eq(null), any(Object[].class), any(Locale.class)))
        .willThrow(new NoSuchMessageException(null));

    // When
    messageKeyResolver.resolveMessage(null);
  }

  @Test(expected = MessageResolutionFailedException.class)
  public void resolveMessageShouldThrowMessageResolutionFailedExceptionWhenMessageKeyIsNotFound()
      throws MessageResolutionFailedException {
    // Given
    given(messageSource
        .getMessage(eq(NON_EXISTENT_MESSAGE_KEY), any(Object[].class), any(Locale.class)))
        .willThrow(new NoSuchMessageException(NON_EXISTENT_MESSAGE_KEY));

    // When
    messageKeyResolver.resolveMessage(NON_EXISTENT_MESSAGE_KEY);
  }

  @Test
  public void resolveMessageShouldResolveMessageWhenMessageKeyExists()
      throws MessageResolutionFailedException {
    // Given
    given(messageSource.getMessage(eq(EXAMPLE_MESSAGE_KEY), any(Object[].class), any(Locale.class)))
        .willReturn(EXAMPLE_MESSAGE);

    // When
    String message = messageKeyResolver.resolveMessage(EXAMPLE_MESSAGE_KEY);

    // Then
    assertThat(message, equalTo(EXAMPLE_MESSAGE));
  }

  @Test
  public void resolveMessageShouldResolveAndInterpolateMessageWhenMessageKeyExistsAndInterpolationIsRequired()
      throws MessageResolutionFailedException {
    // Given
    given(messageSource
        .getMessage(eq(EXAMPLE_WITH_NAME_MESSAGE_KEY), any(Object[].class), any(Locale.class)))
        .willReturn(INTERPOLATED_EXAMPLE_MESSAGE);

    // When
    String message = messageKeyResolver.resolveMessage(EXAMPLE_WITH_NAME_MESSAGE_KEY, NAME);

    // Then
    assertThat(message, equalTo(INTERPOLATED_EXAMPLE_MESSAGE));
  }

  @Test
  public void resolveMessageShouldResolveButNotInterpolateMessageWhenMessageKeyExistsButSubstitutionObjectIsNotProvided()
      throws MessageResolutionFailedException {
    // Given
    given(messageSource
        .getMessage(eq(EXAMPLE_WITH_NAME_MESSAGE_KEY), any(Object[].class), any(Locale.class)))
        .willReturn(EXAMPLE_WITH_NAME_MESSAGE);

    // When
    String message = messageKeyResolver.resolveMessage(EXAMPLE_WITH_NAME_MESSAGE_KEY);

    // Then
    assertThat(message, equalTo(EXAMPLE_WITH_NAME_MESSAGE));
  }

  @Test
  public void resolveMessageOrDefaultShouldReturnMessageWhenMessageKeyExists() {
    // Given
    given(messageSource.getMessage(eq(EXAMPLE_MESSAGE_KEY), any(Object[].class), any(Locale.class)))
        .willReturn(EXAMPLE_MESSAGE);

    // When
    String message =
        messageKeyResolver.resolveMessageOrDefault(EXAMPLE_MESSAGE_KEY);

    // Then
    assertThat(message, equalTo(EXAMPLE_MESSAGE));
  }

  @Test
  public void resolveMessageOrDefaultShouldReturnMessageKeyWhenMessageResolutionFails() {
    // Given
    given(messageSource
        .getMessage(eq(NON_EXISTENT_MESSAGE_KEY), any(Object[].class), any(Locale.class)))
        .willThrow(new NoSuchMessageException(NON_EXISTENT_MESSAGE_KEY));

    // When
    String message =
        messageKeyResolver.resolveMessageOrDefault(NON_EXISTENT_MESSAGE_KEY);

    // Then
    assertThat(message, equalTo(
        MessageKeyResolverImpl.DEFAULT_MESSAGE_PREFIX + NON_EXISTENT_MESSAGE_KEY
            + MessageKeyResolverImpl.DEFAULT_MESSAGE_POSTFIX));
  }
}
