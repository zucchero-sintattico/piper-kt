Feature: Friendship System
  Scenario: User sends a friend request
    Given I am logged in as a user
    And I am on the user's profile page
    When I send a friend request
    Then the user should receive a notification of a new friend request

  Scenario: User accepts a friend request
    Given I am logged in as a user
    And I have a pending friend request
    When I accept the friend request
    Then the requester should be added to my friend list

  Scenario: User rejects a friend request
    Given I am logged in as a user
    And I have a pending friend request
    When I reject the friend request
    Then the requester should not be added to my friend list

  Scenario: Notification for new friend request
    Given I am logged in as a user
    When I receive a new friend request
    Then I should see a notification

  Scenario: Managing user status
    Given I am logged in as a user
    When I go to my profile settings
    Then I should see my current status and last login time

