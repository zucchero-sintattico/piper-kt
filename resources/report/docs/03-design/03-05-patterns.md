# Microservices Patterns

In this section, we will discuss some of the common patterns that we used in our microservices architecture.

## Communication Patterns

### Api Gateway

The API Gateway pattern is used to provide a single entry point for all clients. It is responsible for routing requests to the appropriate services, aggregating the responses, and returning them to the client.

![API Gateway](./img/patterns/api-gateway.jpg)

### Circuit Breaker

The Circuit Breaker pattern is used to prevent cascading failures in a distributed system. It monitors the health of services and opens the circuit when a service is unavailable or slow, allowing requests to fail fast with a specific error message on what went wrong.
This allows clients to handle failures gracefully and recover when the service becomes available again.

### Event Publish/Subscribe

The Event Publish/Subscribe pattern is used to decouple services by allowing them to communicate through events. This pattern is useful when services need to be notified of changes in other services without having to make direct calls to them.

---

## Deployments Patterns

### Service as Containers

The Service as Containers pattern is used to package services as containers. This pattern allows services to be deployed in a consistent and reproducible way, making it easier to manage and scale them.

### Database per Service

Each service has its own database, which is isolated from other services. This pattern allows services to be developed, deployed, and scaled independently of each other.

### Service Discovery

The Service Discovery pattern is used to dynamically discover services at runtime. This pattern allows services to find and communicate with each other without having to know their IP addresses or ports.

The system uses a **Deployment-level Service Discovery** where services register themselves with a service registry when they start up and deregister when they shut down. The service registry is used by clients to discover services and make requests to them.
A **DNS** resolver is used to resolve service names to IP addresses.
It involves:
- **Registration pattern** where services register themselves with a service registry when they start up and deregister when they shut down.
- **Discovery pattern** where clients use the service registry to discover services and make requests to them.

This pattern allows services to be deployed and scaled independently of each other, making it easier to manage and scale the system, allowing an automatic failover and load balancing.

![Service Discovery](./img/patterns/server-side-service-discovery.png)

---

## Observability Patterns

### Health-check API

The Health-check API pattern is used to provide a way for services to report their health status. This pattern is useful for monitoring the health of services and detecting issues before they become critical.

Each service exposes an endpoint that returns its health status. 

### Log aggregation

### Application Metrics?

---

## Security Pattern

### Access Token

The Access Token pattern is used to secure communication between services. This pattern involves issuing access tokens to services that are used to authenticate and authorize requests.

This pattern allows services to verify the identity of other services and control access to resources.
