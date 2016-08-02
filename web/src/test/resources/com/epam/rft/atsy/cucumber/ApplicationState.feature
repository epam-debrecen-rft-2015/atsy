Feature: application state switching

  Background:
    Given The user signed in

  Scenario Outline: user can switch between application states

    Given a candidate with an application
    And the user is on the application page of the candidate
    And the application has the latest state "<state>"
  #And the user has entered all the necessary details of the latest state
    When the user clicks on "<new state>" button
    Then the latest state became "<new state>"

    Examples:

      | state                   | new state               |
      | Új jelentkezés          | Önéletrajzra vár        |
      | Önéletrajzra vár        | HR                      |
      | HR                      | Beugró                  |
      | Beugró                  | Általános technikai kör |
      | Beugró                  | Kódolás                 |
      | Beugró                  | Szakmai beszélgetés     |
      | Beugró                  | Ügyfél beszélgetés      |
      | Általános technikai kör | Kódolás                 |
      | Általános technikai kör | Szakmai beszélgetés     |
      | Általános technikai kör | Ügyfél beszélgetés      |
      | Általános technikai kör | Ajánlat                 |
      | Kódolás                 | Általános technikai kör |
      | Kódolás                 | Szakmai beszélgetés     |
      | Kódolás                 | Ügyfél beszélgetés      |
      | Kódolás                 | Ajánlat                 |
      | Szakmai beszélgetés     | Általános technikai kör |
      | Szakmai beszélgetés     | Kódolás                 |
      | Szakmai beszélgetés     | Ügyfél beszélgetés      |
      | Szakmai beszélgetés     | Ajánlat                 |
      | Ügyfél beszélgetés      | Általános technikai kör |
      | Ügyfél beszélgetés      | Kódolás                 |
      | Ügyfél beszélgetés      | Szakmai beszélgetés     |
      | Ügyfél beszélgetés      | Ajánlat                 |
      | Ajánlat                 | Elfogadás               |
      | Elfogadás               | Belépett                |

  Scenario Outline: user can switch to on hold

    Given a candidate with an application
    And the user is on the application page of the candidate
    And the application has the latest state "<state>"
  #And the user has entered all the necessary details of the latest state
    When the user clicks on "Felvétel szüneteltetve" button
    Then the latest state became "Felvétel szüneteltetve"

    Examples:

      | state                   |
      | Új jelentkezés          |
      | Önéletrajzra vár        |
      | HR                      |
      | Beugró                  |
      | Általános technikai kör |
      | Kódolás                 |
      | Szakmai beszélgetés     |
      | Ügyfél beszélgetés      |
      | Ajánlat                 |
      | Elfogadás               |

  Scenario Outline: user can switch from on hold

    Given a candidate with an application
    And the user is on the application page of the candidate
    And the application has the latest state "Felvétel szüneteltetve"
    When the user clicks on "<state>" button
    Then the latest state became "<state>"

    Examples:

      | state                   |
      | Új jelentkezés          |
      | Önéletrajzra vár        |
      | HR                      |
      | Beugró                  |
      | Általános technikai kör |
      | Kódolás                 |
      | Szakmai beszélgetés     |
      | Ügyfél beszélgetés      |
      | Ajánlat                 |
      | Elfogadás               |

  Scenario Outline: user can switch to denied

    Given a candidate with an application
    And the user is on the application page of the candidate
    And the application has the latest state "<state>"
  #And the user has entered all the necessary details of the latest state
    When the user clicks on "Elutasítva" button
    Then the latest state became "Elutasítva"

    Examples:

      | state                   |
      | Új jelentkezés          |
      | Önéletrajzra vár        |
      | HR                      |
      | Beugró                  |
      | Általános technikai kör |
      | Kódolás                 |
      | Szakmai beszélgetés     |
      | Ügyfél beszélgetés      |
