Feature: New application pop-up

  Background:
    Given The user signed in

  Scenario: new application page appears
    Given the user is on a Candidate A profile page
    When the user clicks on the Új jelentkezés hozzáadása button
    Then the Application page appears
    And the New application page appears
    And the user can select a position from a dropdown
    And the user can select a source of application from the list
    And the user can add a comment
    And the user can submit the new application

  Scenario: new application appears in the applications list without comment
    Given the user is on a New application page of an existing candidate
    And position Fejlesztő exists
    And the user selects Fejlesztő position
    And the user selects direkt source
    And leaves the comment blank
    When the user clicks on the Jelentkezés mentése button
    Then New application page disappears
    And the Application page is displayed
    And application is listed with all details

  Scenario: new application appears in the applications list with comment
    Given the user is on a New application page of an existing candidate
    And position Fejlesztő exists
    And the user selects Fejlesztő position
    And the user selects direkt source
    And the user enters comment Megjegyzés
    When the user clicks on the Jelentkezés mentése button
    Then New application page disappears
    And the Application page is displayed
    And application is listed with all details

  Scenario: new application cannot be saved without position
    Given the user is on a New application page of an existing candidate
    And the user doesn't select a position
    And the user selects direkt source
    And the user enters comment Megjegyzés
    When the user clicks on the Jelentkezés mentése button
    Then the Kérem válasszon egy pozíciót message appears under the position selector

  Scenario: new application cannot be saved without source
    Given the user is on a New application page of an existing candidate
    And position Fejlesztő exists
    And the user selects Fejlesztő position
    And the user doesn't select a source
    And the user enters comment Megjegyzés
    When the user clicks on the Jelentkezés mentése button
    Then the Kérem válasszon egy jelentkezési forrást message appears under the source selector