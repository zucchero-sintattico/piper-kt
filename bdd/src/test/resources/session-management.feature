Feature: Session Management

  Scenario: User communicates with participants in the same session
    Given the user is in an active session
    When the user sends a message to the participants
    Then the participants should receive the message
