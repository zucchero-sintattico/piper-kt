Feature: Channel Management
  Scenario: Admin creates a channel in a server
    Given I am an authenticated user
    And I have created a server
    When I create a new channel in a server
    Then I should be able to access the channel

  Scenario: Admin removes a channel
    Given I am an authenticated user
    And I have created a server
    And there is a channel in the server
    When I remove the channel
    Then the channel should be removed from the server

  Scenario: User sends a message in a text channel
    Given I am an authenticated user
    And I have created a server
    And there is a text channel in the server
    When I send a message in a text channel
    Then the message should be displayed in the channel
