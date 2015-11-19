Feature: Welcome

  Background:
    Given The user signed in

#  Scenario: the Candidates page appears
#    Given the user clicks on the Főoldal button
#    Then the Candidates page appears
#    And the list of candidates appears with the columns: Név, E-Mail, Telefonszám, Pályázott pozíciók
#
#  Scenario: the candidates are sorted by default name ascending
#    Given there are existing candidates:
#      | name        | email                | phone        | positions |
#      | Candidate A | candidate.a@atsy.com | +36105555555 | -         |
#      | Candidate B | candidate.b@atsy.com | +36106666666 | -         |
#      | Candidate C | candidate.c@atsy.com | +36107777777 | -         |
#    When the user clicks on the Főoldal button
#    Then the Candidates page appears
#    And the list of candidates shown in order
#      | name        | email                | phone        | positions |
#      | Candidate A | candidate.a@atsy.com | +36105555555 | -         |
#      | Candidate B | candidate.b@atsy.com | +36106666666 | -         |
#      | Candidate C | candidate.c@atsy.com | +36107777777 | -         |

  Scenario Outline: the candidates list sorting field can be changed
    Given there are existing candidates:
      | name        | email                | phone        | positions |
      | Candidate A | candidate.a@atsy.com | +36105555555 | -         |
      | Candidate B | candidate.b@atsy.com | +36106666666 | -         |
      | Candidate C | candidate.c@atsy.com | +36107777777 | -         |
    When the user clicks on the Főoldal button
    And the user changes the order field to <field>
    Then the Candidates page appears
    And the list of candidates shown ordered by <field> as <order>

    Examples:

      | field     | order     |
      | name      | ascending |
      | email     | ascending |
      | phone     | ascending |
      | positions | ascending |

#  Scenario: the candidates are sorted by default name ascending
#
#    Given the user is logged in
#    And the following candidates exists only:
#
#      |name|email|phone|positions|
#      |Candidate A|candidate.a@atsy.com|+36105555555|-|
#      |Candidate B|candidate.b@atsy.com|+36106666666|-|
#      |Candidate C|candidate.c@atsy.com|+36107777777|-|
#
#    When the user visits the Candidates page
#    Then the candidate details are displayed as:
#
#      |name|email|phone|positions|
#      |Candidate A|candidate.a@atsy.com|+36105555555|-|
#      |Candidate B|candidate.b@atsy.com|+36106666666|-|
#      |Candidate C|candidate.c@atsy.com|+36107777777|-|
#  Scenario Outline: the candidates list sorting field can be changed
#
#    Given the user is on the Candidates page
#    And the following candidates exists only:
#
#      |name|email|phone|positions|
#      |Candidate A|candidate.a@atsy.com|+36105555555|Developer, Tester|
#      |Candidate B|candidate.b@atsy.com|+36106666666||
#      |Candidate C|candidate.c@atsy.com|+36107777777|Tester|
#
#    And the list is not sorted by <field>
#    When the user clicks on the <field> header
#    Then the sorting changes to <field> <order> in the header
#    And the details are displayed ordered by <field> <order>
#
#  Examples:
#
#  |field|order|
#  |name|ascending|
#  |email|ascending|
#  |phone|ascending|
#  |positions|ascending|


