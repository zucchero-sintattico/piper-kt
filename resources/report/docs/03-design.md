# Design

## Microservices Design
As mentioned in the previous sections, the development of the business logic was guided from the outset, by the principles of clean architecture.
Each microservice, was designed and developed in an efficient and technology-independent manner, the code was divided into the following layers:
- *Domain Level* (Entities, Value Objects, Aggregates, Repositories, Factories)
- *Application Level* (Use Cases, Services)
- *Infrastructure Level* (Technologies, Frameworks, Databases, External Services)
- *Interfaces Level* (Controllers, Presenters)

This approach allowed us to develop the business logic in a way that is independent of the technology used, and to be able to change it without affecting the rest of the application.