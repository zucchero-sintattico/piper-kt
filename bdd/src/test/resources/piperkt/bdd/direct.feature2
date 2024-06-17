Feature: Interactions with Friends
  Scenario: User sends a message to a friend
    Given I am logged in as a user
    And I have a friend
    When I send a message to the friend
    Then the friend should receive a notification of the new message

  Scenario: User joins a session with a friend
    Given I am logged in as a user
    And I have a friend
    When I join a session with the friend
    Then I should be able to communicate with the friend in the session