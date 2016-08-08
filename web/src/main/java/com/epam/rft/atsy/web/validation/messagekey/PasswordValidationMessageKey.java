package com.epam.rft.atsy.web.validation.messagekey;


public enum PasswordValidationMessageKey {

  ALL_FIELD_FILLED_RULE("allfieldfilled"),
  CONTAINS_RULE("contains"),
  LENGTH_RULE("length"),
  NEW_PASSWORD_MATCH_RULE("newpasswordmatch"),
  OLD_PASSWORD_MATCH_RULE("oldpasswordmatch"),
  UNIQUE_RULE("unique");


  private static final String PASSWORD_CHANGE_VALIDATION = "passwordchange.validation.";
  private final String messageKey;

  PasswordValidationMessageKey(String messageKey) {
    this.messageKey = PASSWORD_CHANGE_VALIDATION + messageKey;
  }

  public String getMessageKey() {
    return messageKey;
  }
}
