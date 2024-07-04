# Microservices Patterns

In this section, we will discuss some of the common patterns that we used in our microservices architecture.

## Communication Patterns

### Api Gateway

The API Gateway pattern is used to provide a single entry point for all clients. It is responsible for routing requests to the appropriate services, aggregating the responses, and returning them to the client.

![API Gateway](./img/patterns/api-gateway.jpg)

### Event Publish/Subscribe

The Event Publish/Subscribe pattern is used to decouple services by allowing them to communicate through events. This pattern is useful when services need to be notified of changes in other services without having to make direct calls to them.

---

## Deployments Patterns

### Service as Containers

The Service as Containers pattern is used to package services as containers. This pattern allows services to be deployed in a consistent and reproducible way, making it easier to manage and scale them.

### Database per Service

Each service has its own database, which is isolated from other services. This pattern allows services to be developed, deployed, and scaled independently of each other.

---

## Observability Patterns

### Health-check API

The Health-check API pattern is used to provide a way for services to report their health status. This pattern is useful for monitoring the health of services and detecting issues before they become critical.

Each service exposes an endpoint that returns its health status. 

### Log aggregation

### Application Metrics?

---

## Security Pattern

### Access Token

The Access Token pattern is used to secure communication between services. This pattern involves issuing access tokens to services that are used to authenticate and authorize requests.

This pattern allows services to verify the identity of other services and control access to resources.