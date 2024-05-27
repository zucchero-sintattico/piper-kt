Feature: Registration and Authentication

  Scenario: User successfully registers
    Given the user is not registered in the system
    When the user completes the registration process with username "user1" and password "password123"
    Then the user should be authenticated in the system

  Scenario: User successfully logs in
    Given the user is registered in the system
    When the user logs in with username "user1" and password "password123"
    Then the user should be authenticated in the system
