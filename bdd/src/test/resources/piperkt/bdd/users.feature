Feature: User Registration and Authentication

  Scenario: User registers to the system
    Given I am not logged in
    When I make a REGISTER request with valid credentials
    Then I should be registered to the system

  Scenario: User logs in to the system
    Given I am registered
    And I am not logged in
    When I make a LOGIN request with valid credentials
    Then I should be logged in to the system
