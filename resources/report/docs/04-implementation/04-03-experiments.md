# Experiments

## Screaming Architecture

The Screaming Architecture is a software architecture that is designed to be easy to understand and easy to change. It is based on the idea that the architecture should be so clear that it should be able to "scream" what the system does. The Screaming Architecture is a layered architecture that is designed to be easy to understand and easy to change.

The difference is that instead of giving packages names like `controllers`, `services`, `repositories`, etc., we give them names that reflect the business domain. This way, the architecture is more closely aligned with the business domain, making it easier to understand and change.

An example is the `multimedia` service, in which the `application` and `domain` layers are structured following the Screaming Architecture principles, having the inside packages named `direct`, `session`, and `server`, in which we will find the classes that are responsible for the business logic of the multimedia service like the `SessionService` and the event listeners.