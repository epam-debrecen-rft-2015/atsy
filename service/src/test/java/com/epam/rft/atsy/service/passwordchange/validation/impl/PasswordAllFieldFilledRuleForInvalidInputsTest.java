package com.epam.rft.atsy.service.passwordchange.validation.impl;

import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableSet;

import com.epam.rft.atsy.service.domain.PasswordChangeDTO;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordAllFieldFilledValidationException;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.Arrays;
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

  private static final Set<Object> whiteSpaces =
      ImmutableSet.of(EMPTY_STRING, BLANK_SPACES, LINE_FEED, CARRIAGE_RETURN,
          CARRIAGE_RETURN_WITH_LINE_FEED, HORIZONTAL_TABULATOR);

  @Parameter
  public String newPassword;

  @Parameter(value = 1)
  public String newPasswordConfirm;

  @Parameter(value = 2)
  public String oldPassword;

  @Parameter(value = 3)
  public boolean expected;

  private PasswordAllFieldFilledRule rule;

  @Parameters(name = "{index}: ({0},{1},{2})->{3}")
  public static Collection<Object[]> invalidTestData() {
    List<List<Object>> union = new ArrayList<>();

    for (Object whiteSpace : whiteSpaces) {
      List<Object> s1 = Arrays.asList(CONTROL_POSITIVE, whiteSpace, whiteSpace);
      List<Object> s2 = Arrays.asList(CONTROL_POSITIVE, CONTROL_POSITIVE, whiteSpace);

      union.addAll(Collections2.permutations(s1));
      union.addAll(Collections2.permutations(s2));
    }

    return union.stream()
        .distinct()
        .map(ArrayList::new) //Collections2.permutation returns an immutable collection
        .map(e -> {
          e.add(false); //add the expected test result as the 4th parameter
          return e.toArray(); //this transformation is needed, limitation of JUnit
        }).collect(Collectors.toList());
  }

  @Before
  public void init() {
    rule = new PasswordAllFieldFilledRule();
  }

  @Test(expected = PasswordAllFieldFilledValidationException.class)
  public void isValidShouldThrowPasswordAllFieldFilledValidationExceptionForAllNegativeInputs()
      throws PasswordAllFieldFilledValidationException {
    //Given
    PasswordChangeDTO passwordChangeDTO = PasswordChangeDTO.builder()
        .newPassword(this.newPassword)
        .newPasswordConfirm(this.newPasswordConfirm)
        .oldPassword(this.oldPassword)
        .build();

    //When
    rule.validate(passwordChangeDTO);
  }
}
