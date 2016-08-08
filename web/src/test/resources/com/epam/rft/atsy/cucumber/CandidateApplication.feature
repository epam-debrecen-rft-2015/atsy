Feature: Application list

  Background:
    Given The user signed in

  Scenario: the Application list appears on the Candidates page
    Given the user on an existing candidates page
    And there are more than one applications exist for the candidate
    Then the Application list displays and order by last modification date and time desc
    And each application has valid values