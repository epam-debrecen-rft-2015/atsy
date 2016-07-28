Feature: candidate creation test

  Background:
    Given The user signed in

  Scenario Outline: user can't create new candidate because of empty required field

    Given the user is on the Candidate creation page
    And the candidate details are empty
    And the user enters name "name"
    And the user enters e-mail address "email@atsy.com"
    And the user enters phone number "+36301234567"
    And the user enters the place where the candidate has heard about the company "place"
    And the user enters the language level "4"
    And the user clears field <field>
    When the user clicks on the "Mentés" button
    Then a "<message>" message is shown under the <field> field

    Examples:
      | field | message                                    |
      | name  | A jelentkező nevét kötelező megadni!       |
      | email | A jelentkező email címét kötelező megadni! |

  Scenario: user can't create new candidate because of duplication based on same email address

    Given the user is on the Candidate creation page
    And the candidate details are empty
    And another candidate's name is "name"
    And another candidate's e-mail address is "email@atsy.com"
    And another candidate's phone number is "+36301234567"
    And the user enters name "other"
    And the user enters e-mail address "email@atsy.com"
    And the user enters phone number "+36301234568"
    And the user enters the place where the candidate has heard about the company "place"
    And the user enters the language level "4"
    When the user clicks on the "Mentés" button
    Then a "A megadott e-mail című jelentkező már létezik!" message is shown under the email address field

  Scenario: user can't create new candidate because of duplication based on same phone number

    Given the user is on the Candidate creation page
    And the candidate details are empty
    And another candidate's name is "name"
    And another candidate's e-mail address is "email@atsy.com"
    And another candidate's phone number is "+36301234567"
    And the user enters name "other"
    And the user enters e-mail address "other@atsy.com"
    And the user enters phone number "+36301234567"
    And the user enters the place where the candidate has heard about the company "place"
    And the user enters the language level "4"
    When the user clicks on the "Mentés" button
    Then a "A megadott telefonszámú jelentkező már létezik!" message is shown under the phone number field

  Scenario: user can't create new candidate because of name is longer than 100 characters

    Given the user is on the Candidate creation page
    And the candidate details are empty
    And the user enters name longer than 100 characters
    And the user enters e-mail address "email@atsy.com"
    And the user enters phone number "+36301234567"
    And the user enters the place where the candidate has heard about the company "place"
    And the user enters the language level "4"
    When the user clicks on the "Mentés" button
    Then a "A megadott név túl hosszú!" message is shown under the name field

  Scenario: user can't create new candidate because of email address is longer than 255 characters

    Given the user is on the Candidate creation page
    And the candidate details are empty
    And the user enters name "name"
    And the user enters e-mail address longer than 255 characters
    And the user enters phone number "+36301234567"
    And the user enters the place where the candidate has heard about the company "place"
    And the user enters the language level "4"
    When the user clicks on the "Mentés" button
    Then a "A megadott email cím túl hosszú!" message is shown under the email address field

  Scenario: user can't create new candidate because of phone number is longer than 20 characters

    Given the user is on the Candidate creation page
    And the candidate details are empty
    And the user enters name "name"
    And the user enters e-mail address "email@atsy.com"
    And the user enters phone number longer than 20 characters
    And the user enters the place where the candidate has heard about the company "place"
    And the user enters the language level "4"
    When the user clicks on the "Mentés" button
    Then a "A megadott telefonszám túl hosszú!" message is shown under the phone number field

  Scenario: user can't create new candidate because of place is longer than 20 characters

    Given the user is on the Candidate creation page
    And the candidate details are empty
    And the user enters name "name"
    And the user enters e-mail address "email@atsy.com"
    And the user enters phone number "+36301234567"
    And the user enters the place where the candidate has heard about the company longer than 20 characters
    And the user enters the language level "4"
    When the user clicks on the "Mentés" button
    Then a "A megadott hely túl hosszú!" message is shown under the referer field

  Scenario: user can't create new candidate because of email is malformed

    Given the user is on the Candidate creation page
    And the candidate details are empty
    And the user enters name "name"
    And the user enters e-mail address "email/atsy.com"
    And the user enters phone number "+36301234567"
    And the user enters the place where the candidate has heard about the company "place"
    And the user enters the language level "4"
    When the user clicks on the "Mentés" button
    Then a "A jelentkező email címének megfelelő formában kell lennie, például kovacs.jozsef@email.hu!" message is shown under the email address field

  Scenario: user can't create new candidate because of phone number is malformed

    Given the user is on the Candidate creation page
    And the candidate details are empty
    And the user enters name "name"
    And the user enters e-mail address "email@atsy.com"
    And the user enters phone number "+36a301234567"
    And the user enters the place where the candidate has heard about the company "place"
    And the user enters the language level "4"
    When the user clicks on the "Mentés" button
    Then a "A jelentkező telefonszáma egy plusz jellel kezdődhet és utánna számjegyekből állhat!" message is shown under the phone number field