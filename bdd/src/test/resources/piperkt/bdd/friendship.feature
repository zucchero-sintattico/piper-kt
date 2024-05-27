Feature: Friendship System

  Scenario: User sends a friend request
    Given the user is authenticated in the system
    When the user sends a friend request to "friend1"
    Then "friend1" should receive a friend request notification

  Scenario: User accepts a friend request
    Given the user has a friend request from "friend2"
    When the user accepts the friend request
    Then "friend2" should be added to the user's friend list
