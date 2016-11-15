Feature: Alternate Login

  Background:

    Given the user is not authenticated
    And the user is on the login page

  Scenario: 1 UI element check on the Login page

    Then the user should see mandatory images
    And the user should see login fields
    And the user should see login button
    And the focus should be set
    And the browser title should be correct

  Scenario: 2 User exists and logs in

    When the user logs in with valid login details
    Then the user should be logged in

  Scenario: 3 User enters username in different case

    When the user logs in with valid all uppercase username
    Then the user should be logged in

  Scenario: 4 User enters invalid credentials
    When the user tries to log in with incorrect password
    Then  incorrect username or password message should appear

  Scenario: 6 User enters empty username

    When the user tries to log in with empty username
    Then  empty username error message appears above username field

  Scenario: 7 User enters blank username

    When the user tries to log in with blank username
    Then  empty username error message appears above username field

  Scenario: 8 User enters empty password

    When the user tries to log in with empty password
    Then  empty password error message appears above password field

  Scenario: 9 User enters blank password

    When the user tries to log in with blank password
    Then  empty password error message appears above password field

  Scenario: 9 User enters nothing

    When the user tries to log in without filling in the login form
    Then empty username error message appears above username field
    And  empty password error message appears above password field

  Scenario: 10 logged in user is redirected to the Welcome page in case the login page is opened

    When the user logs in with valid login details
    And the user opens the login page
    Then the Candidates page should appear
