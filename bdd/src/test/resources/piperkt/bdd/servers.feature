Feature: Server Management

  Scenario: User creates a new server
    Given I am the logged user
    When I create a new server
    Then I should be able to access the server as owner

  Scenario: User join a server
    Given I am the logged user
    And another user has a server
    When I join the server
    Then I should be able to access the server as member
