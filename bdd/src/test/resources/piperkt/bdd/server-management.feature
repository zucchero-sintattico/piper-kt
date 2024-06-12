Feature: Server Management
  Scenario: User creates a new server
    Given I am logged in as a user
    When I create a new server
    Then the server should be created and I should be the admin

  Scenario: User enters an existing server
    Given I am logged in as a user
    When I join an existing server
    Then I should be able to access the server's resources

  Scenario: Admin removes a member from the server
    Given I am logged in as an admin
    And I am on the server management page
    When I remove a member from the server
    Then the member should no longer have access to the server

  Scenario: Admin modifies server settings
    Given I am logged in as an admin
    And I am on the server settings page
    When I modify the server settings
    Then the changes should be saved
