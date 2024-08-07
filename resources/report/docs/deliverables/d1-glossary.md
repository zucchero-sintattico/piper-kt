# Glossary

The first step in the knowledge crunching phase is to define the ubiquitous language of the project.
The ubiquitous language is a shared language that is used by all team members to discuss the domain of the project.
To define the ubiquitous language, we identified the main terms and concepts related to the project and created a glossary that defines each term and its meaning.

## Global Concepts

| Term               | Definition                                                |
| ------------------ | --------------------------------------------------------- |
| System             | The application that is                                   |
| User               | A person who uses the system and it's registered          |
| User Profile       | Information about a user                                  |
| Message            | A piece of text sent by a user                            |
| Friend             | A user who is added to another user's friend list         |
| Friendship         | A pair of users who have added each other as friends      |
| Friend Request     | A request to add a user as a friend, sent by another user |
| Friendship Message     | A message sent in a friendship                                |
| Friendship Session     | A session dedicated to a friendship                       |
| Server             | A group of channels and members                           |
| Server Profile     | Information about a server                                |
| Owner              | The user who created a server                             |
| Member             | A user who is joined in a server                          |
| Channel            | A room inside a server                                    |
| Message Channel    | A channel used for text chat                              |
| Channel Message    | A message sent in a message channel                       |
| Multimedia Channel | A channel used for video chat                             |
| Multimedia Session | A session dedicated to a multimedia channel or a Direct   |

## Actions

### Users

| Term                  | Definition                                                                         |
| --------------------- | ---------------------------------------------------------------------------------- |
| Registering           | The action performed by a person to create an account and becoming a user          |
| Logging in            | The action performed by a user to enter the system and authenticate himself        |
| Logging out           | The action performed by a user to exit the system                                  |
| Updating user profile | The action performed by a user to change their profile information and preferences |

### Friendships

| Term                       | Definition                                                                  |
| -------------------------- | --------------------------------------------------------------------------- |
| Sending a friend request   | The action performed by a user to send a friend request to another user     |
| Accepting a friend request | The action performed by a user to accept a friend request from another user |
| Rejecting a friend request | The action performed by a user to reject a friend request from another user |
| Sending a friendship message | The action performed by a user to send a message to another user |
| Joining a friendship session | The action performed by a user to enter a multimedia session related to a friendship       |

### Servers

| Term                  | Definition                                                      |
| --------------------- | --------------------------------------------------------------- |
| Creating a server     | The action performed by a user to create a server               |
| Joining a server      | The action performed by a user to enter a server                |
| Leaving a server      | The action performed by a user to exit a server                 |
| Deleting a server     | The action performed by a user to delete a server               |
| Kicking a member      | The action performed by a user to remove a member from a server |
| Update server profile | The action performed by a user to update server settings        |

### Channels

| Term                         | Definition                                                   |
| ---------------------------- | ------------------------------------------------------------ |
| Creating a channel           | The action performed by a user to create a channel           |
| Deleting a channel           | The action performed by a user to delete a channel           |
| Joining a multimedia session | The action performed by a user to enter a session related to a multimedia channel |
| Changing channel settings    | The action performed by a user to change channel settings    |

### Sessions

| Term                       | Definition                                                          |
| -------------------------- | ------------------------------------------------------------------- |
| Leaving a session          | The action performed by a user to exit a session                    |
| Turning on the camera      | The action performed by a user to start the camera in a session     |
| Turning off the camera     | The action performed by a user to stop the camera in a session      |
| Turning on the microphone  | The action performed by a user to start the microphone in a session |
| Turning off the microphone | The action performed by a user to stop the microphone in a session  |

## Bounded-Contexts Glossary

### User Bounded-Context

| Term                  | Definition                                                                 |
| --------------------- | -------------------------------------------------------------------------- |
| User                  | A person who uses the system and it's registered                           |
| User Profile          | Information about a user                                                   |
| Registering           | The action performed by a person to create an account and becoming a user |
| Logging in            | The action performed by a user to enter the system and authenticate himself |
| Logging out           | The action performed by a user to exit the system                          |
| Updating user profile | The action performed by a user to change their profile information and preferences |

### Friendship Bounded-Context

| Term                       | Definition                                                                  |
| -------------------------- | --------------------------------------------------------------------------- |
| Friend                     | A user who is added to another user's friend list                           |
| Friendship                 | A pair of users who have added each other as friends                       |
| Friend Request             | A request to add a user as a friend, sent by another user                  |
| Message         | A message sent in a friendship                                              |                                       |
| Sending a friend request   | The action performed by a user to send a friend request to another user     |
| Accepting a friend request | The action performed by a user to accept a friend request from another user |
| Rejecting a friend request | The action performed by a user to reject a friend request from another user |
| Sending a friendship message | The action performed by a user to send a message to another user |
| Joining a friendship session | The action performed by a user to enter a multimedia session related to a friendship       |

### Server Bounded-Context

| Term                  | Definition                                                      |
| --------------------- | --------------------------------------------------------------- |
| Server                | A group of channels and members                                 |
| Server Settings        | Information about a server                                      |
| Owner                 | The user who created a server                                   |
| Member                | A user who is joined in a server                                |
| Creating a server     | The action performed by a user to create a server               |
| Joining a server      | The action performed by a user to enter a server                |
| Leaving a server      | The action performed by a user to exit a server                 |
| Deleting a server     | The action performed by a user to delete a server               |
| Kicking a member      | The action performed by a user to remove a member from a server |
| Update server settings | The action performed by a user to update server settings        |

### Channel Bounded-Context

| Term                  | Definition                                                   |
| --------------------- | ------------------------------------------------------------ |
| Channel               | A room inside a server                                       |
| Text Channel          | A channel used for text chat                                 |
| Multimedia Channel    | A channel used for video chat                                |
| Message       | A message sent in a message channel                          |
| Channel Settings      | Information about a channel                                   |
| Creating a channel    | The action performed by a user to create a channel           |
| Deleting a channel    | The action performed by a user to delete a channel           |
| Sending a message     | The action performed by a user to send a message in a text channel |
| Changing channel settings | The action performed by a user to change channel settings    |

### Session Bounded-Context

| Term                       | Definition                                                          |
| -------------------------- | ------------------------------------------------------------------- |
| Direct                     | A pair of users with a friendship relationship                     |
| Server                     | A group of channels and members                                    |
| Channel                    | A multimedia room inside a server                                            |
| Session                    | A session dedicated to a friendship or a multimedia channel        |          |
| Joining a multimedia session | The action performed by a user to enter a session related to a direct or a multimedia channel |
| Leaving a session          | The action performed by a user to exit a session                    |
| Turning on the camera      | The action performed by a user to start the camera in a session     |
| Turning off the camera     | The action performed by a user to stop the camera in a session      |
| Turning on the microphone  | The action performed by a user to start the microphone in a session |
| Turning off the microphone | The action performed by a user to stop the microphone in a session  |


