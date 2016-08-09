package com.epam.rft.atsy.web.validation.messagekey;


import com.epam.rft.atsy.service.exception.passwordchange.PasswordAllFieldFilledValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordContainsValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordLengthValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordNewMatchValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordOldMatchValidationException;
import com.epam.rft.atsy.service.exception.passwordchange.PasswordUniqueValidationException;

public enum PasswordValidationMessageKey {

  ALL_FIELD_FILLED_RULE("allfieldfilled", PasswordAllFieldFilledValidationException.class),
  CONTAINS_RULE("contains", PasswordContainsValidationException.class),
  LENGTH_RULE("length", PasswordLengthValidationException.class),
  NEW_PASSWORD_MATCH_RULE("newpasswordmatch", PasswordNewMatchValidationException.class),
  OLD_PASSWORD_MATCH_RULE("oldpasswordmatch", PasswordOldMatchValidationException.class),
  UNIQUE_RULE("unique", PasswordUniqueValidationException.class);


  private static final String PASSWORD_CHANGE_VALIDATION = "passwordchange.validation.";
  private final String messageKey;
  private final Class exceptionClass;

  PasswordValidationMessageKey(String messageKey, Class exceptionClass) {
    this.messageKey = PASSWORD_CHANGE_VALIDATION + messageKey;
    this.exceptionClass = exceptionClass;
  }

  public String getMessageKey() {
    return messageKey;
  }

  public Class getExceptionClass() {
    return exceptionClass;
  }
}
