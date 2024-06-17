Feature: Friendship System

  Scenario: User sends a friend request
    Given I am a logged user
    And another user exists
    When I send a friend request to the other user
    Then the other user should have a pending friend request

  Scenario: User accepts a friend request
    Given I am a logged user
    And another user exists
    And I have a pending friend request from the other user
    When I accept the friend request
    Then the other user should be added to my friend list

  Scenario: User rejects a friend request
    Given I am a logged user
    And another user exists
    And I have a pending friend request from the other user
    When I reject the friend request
    Then the other user should not be added to my friend list


