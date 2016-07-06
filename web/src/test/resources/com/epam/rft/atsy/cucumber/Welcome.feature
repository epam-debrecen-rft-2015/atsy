Feature: Welcome

  Background:
    Given The user signed in

  Scenario: the Candidates page appears
    Given the user clicks on the Főoldal button
    Then the Candidates page appears
    And the list of candidates appears with the columns: Név, E-Mail, Telefonszám, Pályázott pozíciók

  Scenario: the candidates are sorted by default name ascending
    Given there are existing candidates:
      | name        | email                | phone        | positions |
      | Candidate A | candidate.a@atsy.com | +36105555555 | -         |
      | Candidate B | candidate.b@atsy.com | +36106666666 | -         |
      | Candidate C | candidate.c@atsy.com | +36107777777 | -         |
    When the user clicks on the Főoldal button
    Then the Candidates page appears
    And the list of candidates shown in order
      | name        | email                | phone        | positions |
      | Candidate A | candidate.a@atsy.com | +36105555555 | -         |
      | Candidate B | candidate.b@atsy.com | +36106666666 | -         |
      | Candidate C | candidate.c@atsy.com | +36107777777 | -         |

  Scenario Outline: the candidates list sorting field can be changed
    Given there are existing candidates:
      | name        | email                | phone        | positions |
      | Candidate A | candidate.a@atsy.com | +36105555555 | -         |
      | Candidate B | candidate.b@atsy.com | +36106666666 | -         |
      | Candidate C | candidate.c@atsy.com | +36107777777 | -         |
    When the user clicks on the Főoldal button
    And the user changes the order field to <field>, <order>
    Then the Candidates page appears
    And the list of candidates shown ordered by <field> as <order>
  Examples:
      | field     | order |
      | name      | asc   |
      | email     | asc   |
      | phone     | asc   |
      | positions | asc   |
      | name      | desc  |
      | email     | desc  |
      | phone     | desc  |
      | positions | desc  |