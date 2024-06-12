Feature: Session Management
  Scenario: User communicates in a session
    Given I am logged in as a user
    And I am in a session
    When I speak or send messages
    Then other participants should be able to hear or read my communication

  Scenario: User turns on/off the microphone and camera
    Given I am logged in as a user
    And I am in a session
    When I turn on my microphone
    Then others should hear me
    When I turn off my microphone
    Then others should not hear me
    When I turn on my camera
    Then others should see me
    When I turn off my camera
    Then others should not see me