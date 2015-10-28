Feature: Login in

  Scenario: user exists and can login

    Given the user is on the login page
    And the user enters username test
    And the user enters password pass3
    When the user clicks on Bejelentkezés button
    Then the Candidates page appears

  Scenario: user enters wrong password

    Given the user is on the login page
    And the user enters username test
    And the user enters password wrongPwd
    When the user clicks on Bejelentkezés button
    Then Felhasználónév vagy jelszó nem megfelelő! message appears

