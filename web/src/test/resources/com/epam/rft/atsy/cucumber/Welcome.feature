Feature: Welcome
  Scenario: the Candidates page appears

    Given the user is logged in
    When the user clicks on the Főoldal button
    Then the Candidates page appears
    And the list of candidates appears with the columns: Név, E-Mail, Telefonszám, Pályázott pozíciók

  Scenario Outline: the candidates appears in the list of candidates

    Given the user is logged in
    And there is an existing candidate with <name>, <email>, <phone> and <positions>
    When the user visits the Candidates page
    Then the list of candidates contains name <name>
    And email <email>
    And phone <phone>
    And previous positions <positions>

  Examples:

  |name|email|phone|positions|
  |Candidate A|candidate.a@atsy.com|+36105555555|-|
  |Candidate B|candidate.b@atsy.com|+36106666666|-|
  |Candidate C|candidate.c@atsy.com|+36107777777|-|

  Scenario: the candidates are sorted by default name ascending

    Given the user is logged in
    And the following candidates exists only:

      |name|email|phone|positions|
      |Candidate A|candidate.a@atsy.com|+36105555555|-|
      |Candidate B|candidate.b@atsy.com|+36106666666|-|
      |Candidate C|candidate.c@atsy.com|+36107777777|-|

    When the user visits the Candidates page
    Then the candidate details are displayed as:

      |name|email|phone|positions|
      |Candidate A|candidate.a@atsy.com|+36105555555|-|
      |Candidate B|candidate.b@atsy.com|+36106666666|-|
      |Candidate C|candidate.c@atsy.com|+36107777777|-|
  Scenario Outline: the candidates list sorting field can be changed

    Given the user is on the Candidates page
    And the following candidates exists only:

      |name|email|phone|positions|
      |Candidate A|candidate.a@atsy.com|+36105555555|Developer, Tester|
      |Candidate B|candidate.b@atsy.com|+36106666666||
      |Candidate C|candidate.c@atsy.com|+36107777777|Tester|

    And the list is not sorted by <field>
    When the user clicks on the <field> header
    Then the sorting changes to <field> <order> in the header
    And the details are displayed ordered by <field> <order>

  Examples:

  |field|order|
  |name|ascending|
  |email|ascending|
  |phone|ascending|
  |positions|ascending|


