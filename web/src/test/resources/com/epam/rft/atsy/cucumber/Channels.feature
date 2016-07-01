Feature: As the user
  I want to list the existing channels
  so that I can check the open channels

  Background:
    Given The user signed in
    And the Beállítások menu point clicked
    And the options screen appears
    And the channels link is clicked
    And the channels screen appears

  Scenario: user can list existing channels
    Then the list filled with channels appears on the page

  Scenario: user can add new channel
    When the Új csatorna button clicked
    Then the title field cleared

  Scenario Outline: user can save new channel
    When the Új csatorna button clicked
    And user enters "<title>" into the title
    And the Mentés button clicked
    Then the new channel called <title> appears in the list of channels
    Examples:
      | title       |
      | Új csatorna |

  Scenario Outline: user cannot save new channel if channel name exists
    When the Új csatorna button clicked
    And user enters "<title>" into the title
    And the Mentés button clicked
    Then error message appears <message>
    Examples:
      | title       | message                |
      | Új csatorna | Már létezik csatorna   |
      |             | Név megadása kötelező! |

  Scenario: user cancels new channel creation
    Given the Új csatorna button clicked
    And the list saved
    When the Mégsem button clicked
    Then the list of channels left unchanged

  Scenario: user can modify an existing channel
    When the Edit button clicked on a channel
    Then the title field filled

  Scenario Outline: user cant modify the name of an existing channel
    When the Edit button clicked on a channel
    And user enters "<title>" into the title
    And the Mentés button clicked
    Then error message appears <message>
    Examples:
      | title    | message                |
      | facebook | Már létezik csatorna   |
      |          | Név megadása kötelező! |

  Scenario: user can modify an existing channel
    When the Edit button clicked on a channel
    And user enters "Channel edited" into the title
    And the Mentés button clicked
    Then the list of channels changed