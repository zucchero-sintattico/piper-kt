# Microservices Design

When we designed the Microservices structure we have decided to use the **Clean Architecture** pattern, in order to separate the business logic from the technical details, making the system more maintainable, scalable and technology-agnostic.

## Clean Architecture Layers
As mentioned before, the development of the business logic was guided from the outset, by the principles of clean architecture.
Each microservice, was designed and developed in an efficient and technology-independent manner, the code was divided into the following layers:

- _Domain Level_ (Entities, Value Objects, Aggregates, Repositories, Factories)
- _Application Level_ (Use Cases, Services)
- _Infrastructure Level_ (Technologies, Frameworks, Databases, External Services)
- _Interfaces Level_ (Controllers, SocketServers)
- _Configuration Level_ (Configuration Files)
- _Presentation Level_ (Mappers)

This approach allowed us to develop the business logic in a way that is independent of the technology used, and to be able to change it without affecting the rest of the application.


## Users Microservice

### Behavior

Microservice responsible for user management and all user-related operations, including:

- Creation of users in the system (Sign up)
- Authentication of users in the system (Login)
- Changes to the profile of individual users in the system

### Inbound Events

The `Users` bounded context doesn't receive any inbound events.

### Outbound Events

The `Users` bounded context emits the following outbound events:

- `UserRegistered`: emitted when a new user is registered.
- `UserUpdated`: emitted when a user updates their profile.
- `UserLoggedIn`: emitted when a user is authenticated.
- `UserLoggedOut`: emitted when a user logs out.

## Friendships Microservice

### Behavior

Microservice responsible for managing friendships between users, including:

- Friend requests
    - Sending
    - Accepting
    - Rejecting
- Messages between friends

### Inbound Events

The `Friendships` bounded context doesn't receive any inbound events.

### Outbound Events

The `Friendships` bounded context emits the following outbound events:

- `FriendshipRequested`: emitted when a user requests to be friends with another user.
- `FriendshipAccepted`: emitted when a user accepts a friend request.
- `FriendshipRejected`: emitted when a user rejects a friend request.
- `NewMessageInFriendship`: emitted when a new message is sent in a friendship.

## Servers Microservice

### Behavior

Microservice responsible for managing servers and channels, including:

- CRUD operations on servers
- CRUD operations on channels
- Join/Leave/Kick users from server
- Sending and retrieving messages in channels


### Inbound Events

The `Servers` bounded context doesn't receive any inbound events.

### Outbound Events

The `Servers` bounded context emits the following outbound events:

- `ServerCreated`: emitted when a new server is created.
- `ServerUpdated`: emitted when a server is updated.
- `ServerDeleted`: emitted when a server is deleted.
- `ChannelCreated`: emitted when a new channel is created in a server.
- `ChannelUpdated`: emitted when a channel is updated.
- `ChannelDeleted`: emitted when a channel is deleted.
- `NewMessageInChannel`: emitted when a new message is sent in a channel.
- `ServerUserAdded`: emitted when a user is added to a server.
- `ServerUserRemoved`: emitted when a user is removed from a server.
- `ServerUserKicked`: emitted when a user is kicked from a server.

## Multimedia Microservice

### Behavior

Microservice responsible for managing multimedia sessions used to make video/audio calls, including:

- Creation of multimedia sessions
- Joining multimedia sessions
- Leaving multimedia sessions
- Managing audio and video streams

### Inbound Events

The `Multimedia` bounded context listen to the following inbound events:

#### Server Events

- `ServerCreated`: when a new server is created.
- `ServerDeleted`: when a server is deleted.
- `ChannelCreated`: when a new channel is created in a server.
- `ChannelDeleted`: when a channel is deleted.
- `ServerUserAdded`: when a user is added to a server.
- `ServerUserRemoved`: when a user is removed from a server.
- `ServerUserKicked`: when a user is kicked from a server.

Those events are used to keep servers and channels in sync with the multimedia sessions, in order to allow users to make calls in the correct context.

#### Friendships Events

- `FriendshipAccepted`: when a user accepts a friend request.

This event is used to keep the multimedia sessions in sync with the friendships, in order to allow users to make calls to their friends.

### Outbound Events

The `Multimedia` bounded context emits the following outbound events:

- `SessionCreated`: emitted when a new multimedia session is created.
- `SessionDeleted`: emitted when a multimedia session is deleted.
- `AllowedUserAdded`: emitted when a user is allowed to join a multimedia session.
- `AllowedUserRemoved`: emitted when a user is removed from a multimedia session.
- `ParticipantJoined`: emitted when a user joins a multimedia session.
- `ParticipantLeft`: emitted when a user leaves a multimedia session.

## Notifications Microservice

### Behavior

Microservice responsible for managing notifications for the users, including:

- Push notifications for messages
- Push notifications for friend requests
- Updates on the online status of friends
- Updates on server and channel in which the user is a member
- Updates on friends of the user

### Inbound Events

The `Notifications` service listen to most of the outbound events generated by the other services, in order to push notifications to the clients.

### Outbound Events

The `Notifications` bounded context emits the following outbound events:

- `UserOnline`: emitted when a user goes online.
- `UserOffline`: emitted when a user goes offline.

## Frontend Microservice

### Behavior

Microservice responsible for serving the frontend of the application.