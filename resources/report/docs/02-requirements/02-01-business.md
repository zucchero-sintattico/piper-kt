# Business Requirements

The following section describes the business requirements, what the system should able to do, of the Piper-kt project.

## User Login/Signup

A user, who accesses to Piper-kt service, should be able to:

1. Register a new account, which requires providing a username, email, and password.
2. Access the service by authenticating with the username and password, using a previously registered account.

![Login/Signup](./img/business/01-login-signup.jpg)

## Authenticated User

After performing authentication, a user acquires the ability to perform numerous actions, which cover different areas.
The following scenarios can be identified:

- The user now has the ability to manage his profile and is enabled to receive notifications addressed to him.
- The user can use friend request management, which includes the ability to
    - send friendship requests to other users,
    - accept requests received
    - reject requests received.
- The user has the ability to create servers or participate to servers already created.

![Authenticated User](./img/business/02-auth-user.jpg)

## Server Admin

A user, after creating a server, becomes its **administrator**.
This allows access to management features for it, among which the following can be highlighted:

- The administrator can update the server's information or delete it.
- The administrator can remove a user from the server.
- The administrator can create channels (text or multimedia).
- The administrator can update or remove channels already created.

![Server Admin](./img/business/03-servers-admin.jpg)

## Friends interaction

Two users, after befriending each other, have the ability to interact with each other in the following ways:

- Send messages within the chat between the two users.
- Participate in the multimedia session.

![Friends Interaction](./img/business/04-friendship.jpg)

## User in a Server

A user participating in a server has the ability:

- Send messages within text channels available in the server.
- Participate in a session in a multimedia channel available in the server.
- Leave the server.

![User in a Server](./img/business/05-user-in-server.jpg)

## Multimedia Session

A user in a multimedia session, either in a private one with a friend or in a channel, has the ability to manage microphone and webcam and to exit the session.

![Multimedia Session](./img/business/06-session.jpg)
