Feature: candidate modification test

  Background:
    Given The user signed in

  Scenario Outline: user can't modify the existing candidate because the name or e-mail field is empty
    Given the following existing candidates:
      | name        | email                | phone        | referer | languageSkill | description          |
      | Candidate A | candidate.a@atsy.com | +36105555555 | google  | 5             | Elegáns, kicsit furi |

    And the user is on the Candidate profile page of the candidate "Candidate A"
    And the user enters name "<name>"
    And the user enters e-mail address "<email>"
    When the user clicks on the "Mentés" button
    Then a "<error>" message appears under the "<field>" field
    Examples:
      | field | name        | email                | error                                      |
      | name  |             | candidate.a@atsy.com | A jelentkező nevét kötelező megadni!       |
      | email | Candidate A |                      | A jelentkező email címét kötelező megadni! |

  Scenario Outline: user can't modify the existing candidate because of duplication of e-mail or phone

    Given the following existing candidates:
      | name        | email                | phone        | referer | languageSkill | description          |
      | Candidate A | candidate.a@atsy.com | +36105555555 | google  | 5             | Elegáns, kicsit furi |
      | Candidate B | candidate.b@atsy.com | +36106666666 |         | 2             | Össze-vissza beszélt |

    And the user is on the Candidate profile page of the candidate "Candidate A"
    And the user enters e-mail address "<email>"
    And the user enters phone number "<phone>"
    When the user clicks on the "Mentés" button
    Then a "Már létezik ilyen e-mail címmel vagy telefonszámmal jelentkező!" message appears
    Examples:
      | email                | phone        |
      | candidate.b@atsy.com | +36105555555 |
      | candidate.a@atsy.com | +36106666666 |

  Scenario Outline: user can't modify the existing candidate because of violating input field length

    Given the following existing candidates:
      | name        | email                | phone        | referer | languageSkill | description          |
      | Candidate A | candidate.a@atsy.com | +36105555555 | google  | 5             | Elegáns, kicsit furi |

    And the user is on the Candidate profile page of the candidate "Candidate A"
    And the user enters a valid "<what>" longer than "<length>" characters
    When the user clicks on the "Mentés" button
    Then a "<error>" message appears in the listing
    Examples:
      | what    | length | error                              |
      | name    | 100    | A megadott név túl hosszú!         |
      | email   | 255    | A megadott email cím túl hosszú!   |
      | phone   | 20     | A megadott telefonszám túl hosszú! |
      | referer | 20     | A megadott hely túl hosszú!        |

  Scenario: user can't modify the existing candidate because of phone number is not a valid phone number

    Given the following existing candidates:
      | name        | email                | phone        | referer | languageSkill | description          |
      | Candidate A | candidate.a@atsy.com | +36105555555 | google  | 5             | Elegáns, kicsit furi |

    And the user is on the Candidate profile page of the candidate "Candidate A"
    And the user enters a phone number which doesn't match "\\+?[\d]+" pattern
    When the user clicks on the "Mentés" button
    Then a "A jelentkező telefonszáma egy plusz jellel kezdődhet és utána számjegyekből állhat!" message appears under the "phone" field

  Scenario: user can't modify the existing candidate because of email address is not a valid email address

    Given the following existing candidates:
      | name        | email                | phone        | referer | languageSkill | description          |
      | Candidate A | candidate.a@atsy.com | +36105555555 | google  | 5             | Elegáns, kicsit furi |

    And the user is on the Candidate profile page of the candidate "Candidate A"
    And the user enters an invalid email address "email/atsy.com"
    When the user clicks on the "Mentés" button
    Then a "A jelentkező email címének megfelelő formában kell lennie, például kovacs.jozsef@email.hu!" message appears under the "email" field