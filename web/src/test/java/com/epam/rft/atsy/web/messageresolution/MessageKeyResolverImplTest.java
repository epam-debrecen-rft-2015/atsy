package com.epam.rft.atsy.web.messageresolution;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.support.StaticMessageSource;

import java.util.Locale;

@RunWith(MockitoJUnitRunner.class)
public class MessageKeyResolverImplTest {
  private static final String NON_EXISTENT_MESSAGE_KEY = "not.found";

  private static final String BIRTHDAY_MESSAGE_KEY = "happy.birthday";

  private static final String BIRTHDAY_MESSAGE = "Happy Birthday!";

  private static final String BIRTHDAY_WITH_NAME_MESSAGE_KEY = "happy.birthday.with.name";

  private static final String BIRTHDAY_WITH_NAME_MESSAGE = "Happy Birthday, {0}!";

  private static final String NAME = "Snowball";

  private static final String INTERPOLATED_BIRTHDAY_MESSAGE = "Happy Birthday, Snowball!";

  @Spy
  private StaticMessageSource messageSource = new StaticMessageSource();

  @InjectMocks
  private MessageKeyResolverImpl messageKeyResolver;

  private Locale dummyLocale = Locale.ENGLISH;

  @Before
  public void setUp() {
    // ensure that the MessageKeyResolverImpl will use the same locale as the locale of the messages
    // added to the StaticMessageSource
    Locale.setDefault(dummyLocale);
  }

  @Test(expected = MessageResolutionFailedException.class)
  public void resolveMessageShouldThrowMessageResolutionFailedExceptionWhenMessageKeyIsNull() {
    // When
    messageKeyResolver.resolveMessage(null);
  }

  @Test(expected = MessageResolutionFailedException.class)
  public void resolveMessageShouldThrowMessageResolutionFailedExceptionWhenMessageKeyIsNotFound() {
    // When
    messageKeyResolver.resolveMessage(NON_EXISTENT_MESSAGE_KEY);
  }

  @Test
  public void resolveMessageShouldResolveMessageWhenMessageKeyExists() {
    // Given
    messageSource.addMessage(BIRTHDAY_MESSAGE_KEY, dummyLocale, BIRTHDAY_MESSAGE);

    // When
    String message = messageKeyResolver.resolveMessage(BIRTHDAY_MESSAGE_KEY);

    // Then
    assertThat(message, equalTo(BIRTHDAY_MESSAGE));
  }

  @Test
  public void resolveMessageShouldResolveAndInterpolateMessageWhenMessageKeyExistsAndInterpolationIsRequired() {
    // Given
    messageSource
        .addMessage(BIRTHDAY_WITH_NAME_MESSAGE_KEY, dummyLocale, BIRTHDAY_WITH_NAME_MESSAGE);

    // When
    String message = messageKeyResolver.resolveMessage(BIRTHDAY_WITH_NAME_MESSAGE_KEY, NAME);

    // Then
    assertThat(message, equalTo(INTERPOLATED_BIRTHDAY_MESSAGE));
  }

  @Test
  public void resolveMessageShouldResolveButNotInterpolateMessageWhenMessageKeyExistsButSubstitutionObjectIsNotProvided() {
    // Given
    messageSource
        .addMessage(BIRTHDAY_WITH_NAME_MESSAGE_KEY, dummyLocale, BIRTHDAY_WITH_NAME_MESSAGE);

    // When
    String message = messageKeyResolver.resolveMessage(BIRTHDAY_WITH_NAME_MESSAGE_KEY);

    // Then
    assertThat(message, equalTo(BIRTHDAY_WITH_NAME_MESSAGE));
  }
}
