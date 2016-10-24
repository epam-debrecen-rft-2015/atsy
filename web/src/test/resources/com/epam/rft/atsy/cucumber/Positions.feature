Feature: As the user
  I want to list the existing positions
  so that I can check the open positions

  Background:
    Given The user signed in
    And the user clicks on Beállítások-text in the header
    And the user clicks on Pozíciók tab link

  Scenario Outline: 1 UI check
    Then the user should get epam logo in the header
    And the user should get Beállítások link in the header
    And the user should get Kilépés link in the header
    And the user should get Beállítások title
    And the user should get Pozíciók tab link
    And the user should get Csatornák tab link
    And the user should get Jelszó-változtatás tab link
    #TODO And the user should get Pozíciók table with the positions
    # Név, Műveletek, Edit icon and Delete icon test
    And the user should get <title> subtitle
    And the user should get position label
    And the user should get position field
    And the user should get Mentés button
    And the user should get Mégsem button
    Examples:
    | title               |
    | Új pozíció felvétele|

  Scenario: 2 User checks the epam logo in the header
    When the user clicks on epam-logo in the header
    Then welcome url should open

  Scenario: 3 User checks the Beállítások icon in the header
    When the user clicks on Beállítások-icon in the header
    Then settings url should open

  Scenario: 4 User checks the Beállítások text-button in the header
    When the user clicks on Beállítások-text in the header
    Then settings url should open

  Scenario: 5 User checks the Kilépés icon in the header
    When the user clicks on Kilépés-icon in the header
    Then logout url should open

  Scenario: 6 User checks the Kilépés text-button in the header
    When the user clicks on Kilépés-text in the header
    Then logout url should open

  Scenario: 7 User checks the Pozíciók tab link
    When the user clicks on Pozíciók tab link
    Then the user should get Pozíciók table

  Scenario: 8 User checks the Csatornák tab link
    When the user clicks on Csatornák tab link
    Then the user should get Csatornák table

  Scenario: 9 User checks the Jelszó változtatás tab link
    When the user clicks on Jelszó-változtatás tab link
    Then the user should get password change form

  Scenario: 10 User lists existing positions
    #TODO Given at least one position exists
    Then the list filled with positions should appear on the page

  Scenario: 11 User creates new position
    #TODO Given position with Új pozíció title does not exist
    When the user enters Új pozíció into the title
    And the user clicks on Mentés button
    Then the position called Új pozíció should appear in the list of positions
    And the title should be cleared

  Scenario Outline: 12 User enters invalid title at position creation
    #TODO Given position with <title> title exists
    When the user enters <title> into the title
    And the user clicks on Mentés button
    Then <message> message should appear
    Examples:
      | title      | message             |
      | Új pozíció | Már létezik pozíció |
     # | Új pozíció | Már létezik pozíció "Új pozíció" néven ! |

  Scenario Outline: 13 User enters empty title at position creation
    When the user enters <title> into the title
    And the user clicks on Mentés button
    Then <message> message should appear
    Examples:
      | title      | message                |
      |            | Név megadása kötelező! |

  Scenario Outline: 14 User cancels new position creation
    Given the list is saved
    When the user enters <title> into the title
    And the user clicks on Mégsem button
    Then the list of positions should left unchanged
    Examples:
        | title      |
        | Új pozíció |
        |            |

  Scenario: 15 User modifies an existing position
    #TODO Given position with Position edited title does not exist
    When the user clicks on Edit button on a position
    Then the title field should be filled
    When the user enters Position edited into the title
    And the user clicks on Mentés button
    Then the list of positions should be changed

  # bizonyosat módosítson + azt is tesztelje le
  #TODO Scenario Outline: 15b User modifies an existing position
    #TODO Given position with <new_title> title does not exist
    #TODO And position with <old_title> title exists
    #TODO When the user clicks on Edit button on the <old_title> position
    #TODO Then the title field should be filled
    #TODO When the user enters <new_title> into the title
    #TODO And the user clicks on Mentés button
    #TODO Then the position called <old_title> should disappear from the list of positions
    #TODO And the position called <new_title> should appear in the list of positions
    #TODO Examples:
    #TODO   | old_title  | new_title       |
    #TODO   | Új pozíció | Position edited |

  Scenario Outline: 16 User enters invalid title at position modification
    #TODO Given position with <title> title exists
    When the user clicks on Edit button on a position
    And the user enters <title> into the title
    And the user clicks on Mentés button
    Then <message> message should appear
    Examples:
      | title     | message                |
      | Fejlesztő | Már létezik pozíció    |
    # | Új pozíció | Már létezik pozíció "Fejlesztő" néven ! |

  Scenario Outline: 17 User enters empty title at position modification
    When the user clicks on Edit button on a position
    And the user enters <title> into the title
    And the user clicks on Mentés button
    Then <message> message should appear
    Examples:
      | title     | message                |
      |           | Név megadása kötelező! |

  Scenario Outline: 18 User cancels modifying an existing position
    Given the list is saved
    When the user clicks on Edit button on a position
    And the user enters <title> into the title
    And the user clicks on Mégsem button
    Then the list of positions should left unchanged
    Examples:
      | title      |
      | Új pozíció |
      |            |

  # bizonyosat módosítson
  #TODO Scenario Outline: 18b User cancels modifying an existing position
    #TODO Given position with <old_title> title exists
    #TODO And the list is saved
    #TODO When the user clicks on Edit button on the <old_title> position
    #TODO And the user enters <new_title> into the title
    #TODO And the user clicks on Mégsem button
    #TODO Then the list of positions should left unchanged
    #TODO Examples:
    #TODO   | old_title       | new_title  |
    #TODO   | Position edited | Új pozíció |
    #TODO   | Position edited |            |

  #TODO Scenario: 19 User deletes a position
    #TODO Given position with <title> title exists
    #TODO When the user clicks on Delete button on the <title> position
    #TODO Then <message> confirmation message should appear
    #TODO When the user clicks on Yes button
    #TODO Then the position called <title> should disappear from the list of positions
    #TODO #TODO Examples:
    #TODO   | title           | message                                                       |
    #TODO   | Position edited | Biztosan törölni akarod az alábbi pozíciót? (Position edited) |

  #TODO Scenario Outline: 20 User cancels deleting a position
    #TODO Given position with <title> title exists
    #TODO And the list is saved
    #TODO When the user clicks on Delete button on a position
    #TODO Then <message> confirmation message should appear
    #TODO When the user clicks on No button
    #TODO Then the list of positions should left unchanged
    #TODO Examples:
    #TODO   | title     | message                                                 |
    #TODO   | Fejlesztő | Biztosan törölni akarod az alábbi pozíciót? (Fejlesztő) |