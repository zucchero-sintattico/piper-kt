Feature: Server Management

  Scenario: User creates a new server
    Given an authenticated user in the system
    When the user creates a new server with the name "MyServer"
    Then the server "MyServer" should be created successfully

  Scenario: User joins an existing server
    Given the user is authenticated in the system
    When the user joins the existing server "ExistingServer"
    Then the user should be part of the "ExistingServer"
