Feature: candidate modification test

  Background:
    Given The user signed in

  @setup
  Scenario: dummy setup scenario

  Scenario: user can't modify the existing candidate because the name field is empty

    Given the following existing candidates:
      | name            | email              | phone        | referer | languageSkill | description |
      | The Actual Name | candidate@atsy.com | +36301234567 | place   | 3             | desc        |

    And the user is on the Candidate profile page of the candidate "The Actual Name"
    And the user enters name ""
    When the user clicks on the "Mentés" button
    Then a "A jelentkező nevét kötelező megadni!" message appears under the name field

  Scenario: user can't modify the existing candidate because the e-mail address field is empty

    Given the following existing candidates:
      | name            | email              | phone        | referer | languageSkill | description |
      | The Actual Name | candidate@atsy.com | +36301234567 | place   | 3             | desc        |

    And the user is on the Candidate profile page of the candidate "The Actual Name"
    And the user enters e-mail address ""
    When the user clicks on the "Mentés" button
    Then a "A jelentkező email címét kötelező megadni!" message appears under the email address field

  Scenario: user can't modify the existing candidate because of duplication of email address

    Given the following existing candidates:
      | name            | email              | phone         | referer | languageSkill | description |
      | The Actual Name | candidate@atsy.com | +36301234567  | place   | 3             | desc        |
      | existing name   | existing@atsy.com  | +363012345678 | place   | 3             | description |

    And the user is on the Candidate profile page of the candidate "The Actual Name"
    And the user enters e-mail address "existing@atsy.com"
    When the user clicks on the "Mentés" button
    Then a "Már létezik ilyen e-mail címmel vagy telefonszámmal jelentkező!" message appears

  Scenario: user can't modify the existing candidate because of duplication of phone number

    Given the following existing candidates:
      | name            | email              | phone         | referer | languageSkill | description |
      | The Actual Name | candidate@atsy.com | +36301234567  | place   | 3             | desc        |
      | existing name   | existing@atsy.com  | +363012345678 | place   | 3             | description |

    And the user is on the Candidate profile page of the candidate "The Actual Name"
    And the user enters phone number "+363012345678"
    When the user clicks on the "Mentés" button
    Then a "Már létezik ilyen e-mail címmel vagy telefonszámmal jelentkező!" message appears

  Scenario: user can't modify the existing candidate because of name is longer than 100 characters

    Given the following existing candidates:
      | name            | email              | phone        | referer | languageSkill | description |
      | The Actual Name | candidate@atsy.com | +36301234567 | place   | 3             | desc        |

    And the user is on the Candidate profile page of the candidate "The Actual Name"
    And the user enters a name longer than 100 characters
    When the user clicks on the "Mentés" button
    Then a "A megadott név túl hosszú!" message appears in the listing

  Scenario: user can't modify the existing candidate because of email address is longer than 400 characters

    Given the following existing candidates:
      | name            | email              | phone        | referer | languageSkill | description |
      | The Actual Name | candidate@atsy.com | +36301234567 | place   | 3             | desc        |

    And the user is on the Candidate profile page of the candidate "The Actual Name"
    And the user enters a valid email address longer than 255 characters
    When the user clicks on the "Mentés" button
    Then a "A megadott email cím túl hosszú!" message appears in the listing

  Scenario: user can't modify the existing candidate because of phone number is longer than 20 characters

    Given the following existing candidates:
      | name            | email              | phone        | referer | languageSkill | description |
      | The Actual Name | candidate@atsy.com | +36301234567 | place   | 3             | desc        |

    And the user is on the Candidate profile page of the candidate "The Actual Name"
    And the user enters a valid phone number longer than 20 characters
    When the user clicks on the "Mentés" button
    Then a "A megadott telefonszám túl hosszú!" message appears in the listing

  Scenario: user can't modify the existing candidate because of place is longer than 20 characters

    Given the following existing candidates:
      | name            | email              | phone        | referer | languageSkill | description |
      | The Actual Name | candidate@atsy.com | +36301234567 | place   | 3             | desc        |

    And the user is on the Candidate profile page of the candidate "The Actual Name"
    And the user enters a valid place longer than 20 characters
    When the user clicks on the "Mentés" button
    Then a "A megadott hely túl hosszú!" message appears in the listing

  Scenario: user can't modify the existing candidate because of phone number is not a valid phone number

    Given the following existing candidates:
      | name            | email              | phone        | referer | languageSkill | description |
      | The Actual Name | candidate@atsy.com | +36301234567 | place   | 3             | desc        |

    And the user is on the Candidate profile page of the candidate "The Actual Name"
    And the user enters a phone number which doesn't match "\\+?[\d]+" pattern
    When the user clicks on the "Mentés" button
    Then a "A jelentkező telefonszáma egy plusz jellel kezdődhet és utánna számjegyekből állhat!" message appears under the phone number field

  Scenario: user can't modify the existing candidate because of email address is not a valid email address

    Given the following existing candidates:
      | name            | email              | phone        | referer | languageSkill | description |
      | The Actual Name | candidate@atsy.com | +36301234567 | place   | 3             | desc        |

    And the user is on the Candidate profile page of the candidate "The Actual Name"
    And the user enters an invalid email address "email/atsy.com"
    When the user clicks on the "Mentés" button
    Then a "A jelentkező email címének megfelelő formában kell lennie, például kovacs.jozsef@email.hu!" message appears under the email address field