package com.epam.rft.atsy.service.passwordchange.validation.impl;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RunWith(Parameterized.class)
public class PasswordAllFieldFilledRuleForInvalidInputsTest {

  private static final String EMPTY_STRING = StringUtils.EMPTY;
  private static final String BLANK_SPACES = "    ";
  private static final String LINE_FEED = "\n";
  private static final String CARRIAGE_RETURN = "\r";
  private static final String CARRIAGE_RETURN_WITH_LINE_FEED = CARRIAGE_RETURN + LINE_FEED;
  private static final String HORIZONTAL_TABULATOR = "\t";
  private static final String CONTROL_POSITIVE = "foo";
  @Parameter
  public String newPassword;
  @Parameter(value = 1)
  public String newPasswordConfirm;
  @Parameter(value = 2)
  public String oldPassword;
  @Parameter(value = 3)
  public boolean expected;
  private PasswordAllFieldFilledRule rule;

  @Parameters(name = "{index}: ({0},{1},{2}) -> {3}")
  public static Collection<Object[]> invalidTestData() {

    final Set<Object> whiteSpaces =
        ImmutableSet.of(EMPTY_STRING, BLANK_SPACES, LINE_FEED, CARRIAGE_RETURN,
            CARRIAGE_RETURN_WITH_LINE_FEED, HORIZONTAL_TABULATOR);

    List<Object[]> domain = Sets.powerSet(whiteSpaces)
        .stream()
        .filter(s -> s.size() <= 3 && !s.isEmpty())
        .map(ArrayList::new)
        .map(s -> {
          while (s.size() < 3) {
            s.add(CONTROL_POSITIVE);
          }
          s.add(false); //add the expected test result
          return s.toArray();
        })
        .collect(Collectors.toList());

    return domain;
  }

  @Before
  public void init() {
    rule = new PasswordAllFieldFilledRule();
  }

  @Test
  public void isValidShouldReturnFalseForAllNegativeInputs() {
    //Given
    PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder()
        .newPassword(this.newPassword)
        .newPasswordConfirm(this.newPasswordConfirm)
        .oldPassword(this.oldPassword)
        .build();

    //Then
    assertThat(rule.isValid(passwordChangeDTO), is(false));
  }
}
