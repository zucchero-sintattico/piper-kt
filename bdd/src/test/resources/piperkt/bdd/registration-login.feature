Feature: User Registration and Authentication
  Scenario: User registers to the system
    Given I am on the registration page
    When I fill in the registration form with valid data
    And I submit the form
    Then I should see a confirmation message
    And I should be able to log in with the new account

  Scenario: User logs in to the system
    Given I am on the login page
    When I fill in the login form with valid credentials
    And I submit the form
    Then I should be logged into the system
