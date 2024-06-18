Feature: Server Management

  Scenario: User creates a new server
    Given I am the logged user
    When I create a new server
    Then I should be able to access the server as owner

  Scenario: User creates a channel in a server
    Given I am the logged user
    And I have a server
    When I create a new channel in a server
    Then I should be able to access the channel

  Scenario: User join a server
    Given I am the logged user
    And another user has a server
    When I join the server
    Then I should be able to access the server as member
