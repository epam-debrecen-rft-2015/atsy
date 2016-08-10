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
    And the user clears field "<field>"
    When the user clicks on the "Mentés" button
    Then a "<message>" message is shown under the "<field>" field

    Examples:
      | field | message                                    |
      | name  | A jelentkező nevét kötelező megadni!       |
      | email | A jelentkező email címét kötelező megadni! |

  Scenario Outline: user can't create new candidate because of duplication based on same email address or phone number

    Given there are existing candidates:
      | name        | email                | phone        | positions |
      | Candidate A | candidate.a@atsy.com | +36105555555 | -         |
      | Candidate B | candidate.b@atsy.com | +36106666666 | -         |
      | Candidate C | candidate.c@atsy.com | +36107777777 | -         |
    Given the user is on the Candidate creation page
    And the candidate details are empty
    And another candidate's "<field>" is "<duplicate>"
    And the user enters name "other"
    And the user enters e-mail address "<email>"
    And the user enters phone number "<phone>"
    And the user enters the place where the candidate has heard about the company "place"
    And the user enters the language level "4"
    When the user clicks on the "Mentés" button
    Then a "Már létezik ilyen e-mail címmel vagy telefonszámmal jelentkező!" message is shown on the top of the page

    Examples:
      | field | duplicate            | phone        | email                |
      | email | candidate.c@atsy.com | +36301234568 | candidate.c@atsy.com |
      | phone | +36105555555         | +36105555555 | email@atsy.com       |

  Scenario Outline: user can't create new candidate because of input length violation

    Given the user is on the Candidate creation page
    And the candidate details are empty
    #these steps required because these fields are needed to be filled
    And the user enters name "<name>"
    And the user enters e-mail address "<email>"
    And the user enters phone number "<phone>"
    #this is where we violate the input length
    And the user enters "<field>" longer than "<length>" characters
    And the user enters the language level "4"
    When the user clicks on the "Mentés" button
    Then a "<message>" message appears
    Examples:
      | field   | length | message                            | email          | name | phone        |
      | name    | 100    | A megadott név túl hosszú!         | email@atsy.com |      | +36301234568 |
      | email   | 255    | A megadott email cím túl hosszú!   |                | foo  | +36301234568 |
      | phone   | 20     | A megadott telefonszám túl hosszú! | email@atsy.com | foo  |              |
      | referer | 20     | A megadott hely túl hosszú!        | email@atsy.com | foo  | +36301234568 |

  Scenario Outline: user can't create new candidate because of email address
  or phone number is malformed

    Given the user is on the Candidate creation page
    And the candidate details are empty
    And the user enters name "name"
    And the user enters e-mail address "<email>"
    And the user enters phone number "<phone>"
    And the user enters the place where the candidate has heard about the company "place"
    And the user enters the language level "4"
    When the user clicks on the "Mentés" button
    Then a "<message>" message is shown under the "<field>" field

    Examples:
      | field | email          | phone         | message                                                                                    |
      | email | email/atsy.com | +36301234567  | A jelentkező email címének megfelelő formában kell lennie, például kovacs.jozsef@email.hu! |
      | phone | email@atsy.com | +36a301234567 | A jelentkező telefonszáma egy plusz jellel kezdődhet és utánna számjegyekből állhat!       |