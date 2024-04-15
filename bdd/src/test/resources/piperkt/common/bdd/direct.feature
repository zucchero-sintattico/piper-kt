Feature: Interactions with Friends

  Scenario: User sends a message to a friend
    Given the user is authenticated and has friends in the list
    When the user sends a message to "friend3"
    Then "friend3" should receive a new message
