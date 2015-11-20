Feature: As the user
  I want to list the existing positions
  so that I can check the open positions

  Background:
    Given The user signed in
    When the Beállítások menu point clicked
    Then the options screen appears

  Scenario: user can list existing positions
    Given The user signed in
    And the Beállítások menu point clicked
    When the options screen appears
    Then the list filled with positions appears on the page

  Scenario: user can list existing positions
    Given The user signed in
    And the Beállítások menu point clicked
    And the options screen appears
    When the "Új pozíció" button clicked
    Then the title field cleared