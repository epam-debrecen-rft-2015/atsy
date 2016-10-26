Feature: Login in

  Background:

    Given the user opens the login page

  Scenario: 1 UI element check on the Login page

    Then the user should get epam logo
    And the user should get name field
    And the user should get password field
    And the user should get Bejelentkezés button
    And the user should see placeholder Felhasználónév in the name field
    And the user should see placeholder Jelszó in the password field

  Scenario: 2 Username inputfield is automatically in focus

    Then the username field should be in focus

  Scenario: 3 Browser title is correct

    Then the browser title should be Applicant Tracking System

  Scenario Outline: 4 User exists and logs in

    When the user enters username <username>
    And the user enters password <password>
    And the user clicks on Bejelentkezés button
    Then the Candidates page should appear
    Examples:
      | username | password |
      | test     | pass3    |
      | TEST     | pass3    |

  Scenario Outline: 5 User enters invalid username AND/OR password

    And the user enters username <username>
    And the user enters password <password>
    When the user clicks on Bejelentkezés button
    Then  <error message> message should appear
    Examples:
      | username    | password  | error message                             |
      | test        | wrongPwd  | Felhasználónév vagy jelszó nem megfelelő! |
      | wrongUname  | pass3     | Felhasználónév vagy jelszó nem megfelelő! |
      | wrongUname  | wrongPwd  | Felhasználónév vagy jelszó nem megfelelő! |
      | tes t       | pass3     | Felhasználónév vagy jelszó nem megfelelő! |
      | test        | pass 3    | Felhasználónév vagy jelszó nem megfelelő! |

  Scenario Outline: 6 User enters empty username AND/OR password

    And the user enters username <username>
    And the user enters password <password>
    When the user clicks on Bejelentkezés button
    Then  <error message> message should appear above the <field_name> field
    Examples:
      | username | password | error message                 | field_name |
      |          | pass3    | Add meg a felhasználó neved!  | name       |
      | test     |          | Add meg a jelszavad!          | password   |
      |          |          | Add meg a felhasználó neved!  | name       |
      |          |          | Add meg a jelszavad!          | password   |

    Scenario: 7 logged in user is redirected to the Welcome page in case the login page is opened

      When the user enters username test3
      And the user enters password pass3
      And the user clicks on Bejelentkezés button
      And the user opens the login page
      Then the Candidates page should appear
