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

  private static final String EXAMPLE_MESSAGE_KEY = "happy.birthday";

  private static final String EXAMPLE_MESSAGE = "Happy Birthday!";

  private static final String EXAMPLE_WITH_NAME_MESSAGE_KEY = "happy.birthday.with.name";

  private static final String EXAMPLE_WITH_NAME_MESSAGE = "Happy Birthday, {0}!";

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

    messageSource.addMessage(EXAMPLE_MESSAGE_KEY, dummyLocale, EXAMPLE_MESSAGE);

    messageSource
        .addMessage(EXAMPLE_WITH_NAME_MESSAGE_KEY, dummyLocale, EXAMPLE_WITH_NAME_MESSAGE);
  }

  @Test(expected = MessageResolutionFailedException.class)
  public void resolveMessageShouldThrowMessageResolutionFailedExceptionWhenMessageKeyIsNull()
      throws MessageResolutionFailedException {
    // When
    messageKeyResolver.resolveMessage(null);
  }

  @Test(expected = MessageResolutionFailedException.class)
  public void resolveMessageShouldThrowMessageResolutionFailedExceptionWhenMessageKeyIsNotFound()
      throws MessageResolutionFailedException {
    // When
    messageKeyResolver.resolveMessage(NON_EXISTENT_MESSAGE_KEY);
  }

  @Test
  public void resolveMessageShouldResolveMessageWhenMessageKeyExists()
      throws MessageResolutionFailedException {
    // When
    String message = messageKeyResolver.resolveMessage(EXAMPLE_MESSAGE_KEY);

    // Then
    assertThat(message, equalTo(EXAMPLE_MESSAGE));
  }

  @Test
  public void resolveMessageShouldResolveAndInterpolateMessageWhenMessageKeyExistsAndInterpolationIsRequired()
      throws MessageResolutionFailedException {
    // When
    String message = messageKeyResolver.resolveMessage(EXAMPLE_WITH_NAME_MESSAGE_KEY, NAME);

    // Then
    assertThat(message, equalTo(INTERPOLATED_BIRTHDAY_MESSAGE));
  }

  @Test
  public void resolveMessageShouldResolveButNotInterpolateMessageWhenMessageKeyExistsButSubstitutionObjectIsNotProvided()
      throws MessageResolutionFailedException {
    // When
    String message = messageKeyResolver.resolveMessage(EXAMPLE_WITH_NAME_MESSAGE_KEY);

    // Then
    assertThat(message, equalTo(EXAMPLE_WITH_NAME_MESSAGE));
  }
}
