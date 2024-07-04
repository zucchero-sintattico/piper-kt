# Bounded Contexts

The system is divided into the following bounded contexts, according to the identified pivotal points:
- **Users** context
- **Friendships** context
- **Servers** context
- **Multimedia** context

Following is a detailed description of each bounded context.

## Users

The `Users` bounded context is responsible for managing the users, their profiles and authentication.

![Users Context](./img/bc/ddd-bc-users.jpg)

## Friendships

The `Friendships` bounded context is responsible for managing the friendships between users and their messages.

![Friendships Context](./img/bc/ddd-bc-friendships.jpg)

## Servers

The `Servers` bounded context is responsible for managing the servers and channels, including the messages sent in the channels.

![Servers Context](./img/bc/ddd-bc-servers.jpg)

## Multimedia

The `Multimedia` bounded context is responsible for managing multimedia sessions used to make video/audio calls.

![Multimedia Context](./img/bc/ddd-bc-multimedia.jpg)

