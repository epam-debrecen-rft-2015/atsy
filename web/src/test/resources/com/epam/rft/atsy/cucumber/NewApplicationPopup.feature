Feature: New application pop-up

  Background:
    Given The user signed in

  Scenario: new application popup appears
    Given the user is on a Candidate A profile page
    When the user clicks on the Új jelentkezés hozzáadása button
    Then the Application page appears
    And the New application popup appears
    And the user can select a position from a dropdown
    And the user can select a source of application from the list
    And the user can add a comment
    And the user can submit the new application

