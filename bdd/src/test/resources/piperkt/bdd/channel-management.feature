Feature: Channel Management
  Scenario: Admin creates a new channel
    Given I am logged in as an admin
    When I create a new channel
    Then the channel should be available for users

  Scenario: Admin removes a channel
    Given I am logged in as an admin
    And I am on the channel management page
    When I remove a channel
    Then the channel should no longer be accessible

  Scenario: User sends a message in a text channel
    Given I am logged in as a user
    And I am in a text channel
    When I send a message
    Then other users in the channel should receive a notification

  Scenario: User accesses a multimedia channel
    Given I am logged in as a user
    And I am in a multimedia channel
    When I join the channel
    Then I should be able to access the multimedia content