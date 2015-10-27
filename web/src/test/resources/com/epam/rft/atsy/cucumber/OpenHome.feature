Feature: Open up the login page

  Scenario: As a developer I want Login Page to be loaded
    When I'm opening http://localhost:8080/atsy/login
    Then I'm happy

  Scenario: user exists and can login

    Given the user is on the login page
    And his username is "user"
    And his password is "password"
    And the user enters username "user"
    And the user enters password "password"
    When the user clicks on Bejelentkezés button
    Then the Candidates page appears

