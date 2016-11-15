Feature: Alternate Login

  Background:

    Given the user is not authenticated
    And the user opens the login page

  Scenario: 1 UI element check on the Login page

    Then the user should see mandatory images
    And the user should see login fields
    And the user should see login button
    And the focus should be set
    And the browser title should be correct

  Scenario: 2 User exists and logs in

    When the user logs in with valid login details
    Then the user should be logged in

  Scenario Outline: 3 User enters username in different case

    When the user logs in with valid <case> username
    Then the user should be logged in
    Examples:
    | case |
    | uppercase |
    | lowercase |
    | camelcase |

  Scenario Outline: 4 User enters invalid credentials
    When the user tries to log in with <username> name and <password> password
    Then  incorrect username or password message should appear
    Examples:
    | username | password |
    | valid    | invalid  |
    | invalid  | valid    |
    | invalid  | invalid  |
    | hasSpace | valid    |
    | valid    | hasSpace |
    | hasSpace | hasSpace |

  Scenario Outline: 6 User enters empty username

    When the user tries to log in with <username> name
    Then  empty username error message appears above username field
    Examples:
    | username |
    | empty    |
    | blank    |

  Scenario Outline: 7 User enters empty password

    When the user tries to log in with <password> password
    Then  empty password error message appears above password field
    Examples:
    | password |
    | empty    |
    | blank    |

  Scenario Outline: 8 User enters nothing

    When the user tries to log in with <username> name and <password> password
    Then empty username error message appears above username field
    And  empty password error message appears above password field
    Examples:
    | username | password |
    | empty    | empty    |
#    | blank    | empty    |
    | empty    | blank    |
#    | blank    | blank    |

  Scenario: 9 logged in user is redirected to the Welcome page in case the login page is opened

    When the user logs in with valid login details
    And the user opens the login page
    Then the user should be logged in
