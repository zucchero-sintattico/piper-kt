Feature: Interactions with Friends

  Scenario: User sends a message to a friend
    Given I am logged user
    And I have a friend
    When I send a message to the friend
    Then the friend should receive the message
