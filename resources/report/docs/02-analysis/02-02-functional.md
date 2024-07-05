# Functional Requirements

## Requirements Overview

1. **Registration** and **Authentication**:
    1. Ability to register to the system.
    2. Ability to login into the system.

2. **Friendship** system:
    1. Ability to send friend requests to other users.
    2. Possibility of *accepting* or *rejecting* friend requests.
    3. Notification system for receiving new friend requests.
    4. System for managing the status (online) and last login time of users.

3. **Interactions** with friends:
    1. Messaging management among friends.
    2. Notification system for the arrival of new messages.
    3. Ability to join a session with a friend.

4. **Server** management:
    1. Possibility of creating a new server.
    2. Possibility of entering an existing server.
    3. [Server Admin] Possibility to remove members from the server.
    4. [Server Admin] Possibility to modify the server settings.

5. **Channel** management:
    1. [Server Admin] Possibility of creation of channels (text or multimedia).
    2. [Server Admin] Possibility of removing channels.
    3. Messaging system for text channels.
    4. Notification system for new messages sent within textual channels.
    5. Ability to access the session of multimedia channels.

6. **Session** management:
    1. Ability to communicate through other participants in the same session.
    2. Ability to turn on and off the microphone and camera.

## User Stories
In order to capture and describe the functional requirements listed before, we have decided to use the User Stories approach.

These user stories are written from the perspective of the end-users and describe the features and functionalities that they expect from the application.

### Registration and Authentication

1. **User Registration**\
    As a **new user**,\
    I want to register to the system,\
    so that I can create an account and start using the application.

2. **User Login**\
    As a **registered user**,\
    I want to log into the system,\
    so that I can access my account and use the application's features.

### Friendship System

1. **Send Friend Requests**\
    As a **user**,\
    I want to send friend requests to other users,\
    so that I can connect with them and add them to my friends list.

2. **Accept/Reject Friend Requests**\
    As a **user**,\
    I want to accept or reject friend requests,\
    so that I can manage my connections and decide who I want to be friends with.

3. **Friend Request Notifications**\
    As a **user**,\
    I want to receive notifications for new friend requests,\
    so that I am aware when someone wants to connect with me.

4. **Manage Status and Last Login**\
    As a **user**,\
    I want to manage my online status and see the last login time of my friends,\
    so that I can know when they are available to interact.

### Interactions with Friends

1. **Messaging Friends**\
    As a **user with friends**,\
    I want to send and receive messages with my friends,\
    so that we can communicate through the application.

2. **Message Notifications**\
    As a **user**,\
    I want to receive notifications for new messages,\
    so that I know when I have a new message from a friend.

3. **Join Friend's Session**\
    As a **user with friends**,\
    I want to join a session with a friend,\
    so that we can interact in real-time within the same session.

### Server Management

1. **Create New Server**\
    As a **user**,\
    I want to create a new server,\
    so that I can have a dedicated space for group interactions.

2. **Join Existing Server**\
    As a **user**,\
    I want to join an existing server,\
    so that I can participate in its activities and discussions.

3. **Remove Members from Server [Server Admin]**\
    As a **server admin**,\
    I want to remove members from the server,\
    so that I can manage who has access to the server.

4. **Modify Server Settings [Server Admin]**\
    As a **server admin**,\
    I want to modify the server settings,\
    so that I can customize the server to meet the needs of its members.

### Channel Management
 
1. **Create Channels [Server Admin]**\
    As a **server admin**,\
    I want to create text or multimedia channels,\
    so that members can have organized spaces for different types of communication.

2. **Remove Channels [Server Admin]**\
    As a **server admin**,\
    I want to remove channels,\
    so that I can manage and declutter the server by removing unnecessary channels.

3. **Text Channel Messaging**\
    As a **server member**,\
    I want to send and receive messages in text channels,\
    so that I can communicate with others in the server.

4. **Text Channel Message Notifications**\
    As a **server member**,\
    I want to receive notifications for new messages in text channels,\
    so that I am aware of new conversations and updates.

5. **Access Multimedia Channels**\
    As a **server member**,\
    I want to access multimedia channels,\
    so that I can join and participate in multimedia sessions.

### Session Management

1. **Join Session**
    As a **user**,\
    I want to join multimedia session,\
    so that I can start videochat.

2. **Leave Session**
    As a **session participant**,\
    I want to leave multimedia session,\
    so that I can stop videochat.

2. **Microphone and Camera Control**\
    As a **session participant**, \
    I want to manage my audio/video,\
    so that I can control what others see.


## Feature Specifications (BDD)

Once the user stories are defined, the first thing that we did was proceeded to create some feature specifications that capture and describe the details of each user story. 

To do this, we used the Behavior-Driven Development (BDD) approach, which focuses on defining the behavior of the system from the perspective of the end-users. The feature specifications are written in a structured format that includes the following sections, using the Gherkin syntax:

- **Feature**: A high-level description of the feature or functionality.

- **Scenario**: A specific use case or situation that describes the behavior of the system.

- **Given**: The initial context or state of the system before the scenario starts.

- **When**: The action or event that triggers the scenario.

- **Then**: The expected outcome or result of the scenario.

The feature specifications provide a detailed description of the expected behavior of the system based on the user stories.
In this way, we were able to define some Acceptance Criteria that will be used to validate the implementation of the features, useful both for the development team and the stakeholders.

You can find an example of a feature specification below:

```gherkin
Feature: User Registration and Authentication

  Scenario: User registers to the system
    Given I am not logged in
    When I make a REGISTER request with valid credentials
    Then I should be registered to the system

  Scenario: User logs in to the system
    Given I am registered
    And I am not logged in
    When I make a LOGIN request with valid credentials
    Then I should be logged in to the system

Feature: Channel Management

  Scenario: Admin creates a channel in a server
    Given I am an authenticated user
    And I have created a server
    When I create a new channel in a server
    Then I should be able to access the channel

  Scenario: Admin removes a channel
    Given I am an authenticated user
    And I have created a server
    And there is a channel in the server
    When I remove the channel
    Then the channel should be removed from the server

  Scenario: User sends a message in a text channel
    Given I am an authenticated user
    And I have created a server
    And there is a text channel in the server
    When I send a message in a text channel
    Then the message should be displayed in the channel

Feature: Server Management

  Scenario: User creates a new server
    Given I am the logged user
    When I create a new server
    Then I should be able to access the server as owner

  Scenario: User join a server
    Given I am the logged user
    And another user has a server
    When I join the server
    Then I should be able to access the server as member

Feature: Interactions with Friends

  Scenario: User sends a message to a friend
    Given I am logged user
    And I have a friend
    When I send a message to the friend
    Then the friend should receive the message

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
```