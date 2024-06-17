Feature: Server Management

  Scenario: User creates a new server
    Given I am the logged user
    When I create a new server
    Then I should be able to access the server


