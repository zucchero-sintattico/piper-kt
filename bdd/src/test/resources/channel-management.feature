Feature: Channel Management

  Scenario: User creates a text channel
    Given the user is the owner of a server
    When the user creates a new text channel named "TextChannel"
    Then the channel "TextChannel" should be created successfully

  Scenario: User sends a message in a text channel
    Given the user is part of a server with a text channel named "TextChannel"
    When the user sends a message in "TextChannel"
    Then all channel members should receive a notification
