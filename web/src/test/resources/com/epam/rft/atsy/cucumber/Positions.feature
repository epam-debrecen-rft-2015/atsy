Feature: As the user
  I want to list the existing positions
  so that I can check the open positions

  Background:
    Given The user signed in
    And the Beállítások menu point clicked
    And the options screen appears
    And the positions link is clicked
    And the positions screen appears

  Scenario: user can list existing positions
    Then the list filled with positions appears on the page

  Scenario: user can add new position
    When the Új pozíció button clicked
    Then the title field cleared

  Scenario Outline: user can save new position
    When the Új pozíció button clicked
    And user enters "<title>" into the title
    And the Mentés button clicked
    Then the new position called <title> appears in the list of positions
    Examples:
      | title      |
      | Új pozíció |

  Scenario Outline: user can save new position
    When the Új pozíció button clicked
    And user enters "<title>" into the title
    And the Mentés button clicked
    Then error message appears <message>
    Examples:
      | title      | message                |
      | Új pozíció | Már létezik pozíció    |
      |            | Név megadása kötelező! |

  Scenario: user cancels new position creation
    Given the Új pozíció button clicked
    And the list saved
    When the Mégsem button clicked
    Then the list of positions left unchanged

  Scenario: user can modify an existing position
    When the Edit button clicked on a position
    Then the title field filled

  Scenario Outline: user cant modify the name of an existing position
    When the Edit button clicked on a position
    And user enters "<title>" into the title
    And the Mentés button clicked
    Then error message appears <message>
    Examples:
      | title     | message                |
      | Fejlesztő | Már létezik pozíció    |
      |           | Név megadása kötelező! |

  Scenario: user can modify an existing position
    When the Edit button clicked on a position
    And user enters "Position edited" into the title
    And the Mentés button clicked
    Then the list of positions changed