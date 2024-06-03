# Conclusions

## Future Work

### User Photo Management

We aimed to develop a photo management system that allows users to upload and manage their photos with a file service that allow upload and retrieve of images.
In this way we only have to save the path of the image in the database and not the image itself.
The actual state of the user service is implemented in that way but we have to implement the file service, the idea was to use some pre-built file-storage but we didn't have time to implement it, so we decided to postpone it to the future.

### Reactive Implementation

The Micronaut framework offers both tools for implementing microservices in a synchronously and asynchronously ways.
The actual state of the application is implemented in a synchronous way, just for simplicity, as that is not the main goal of the project.
This choice has effects on the performance of the application, so one of the future works would be to refactor the application to use asynchronous programming.
This would allow the application to handle more requests at the same time and improve the performance of the system.

By the way as Micronaut reached its fourth version, it has the support for virtual threads, improving the performance of the application, even if implemented in a synchronous way.

## Performance Problems

We encountered some performance problems during the development of the project getting a result of 10 requests per second handled by the microservice.
We found out that if the code doesn't publish on Kafka, the performance of the application is improved to 2000 requests per second.
This is probably due to the fact that the Kafka client is not asynchronous and it blocks the thread until the message is sent, as we are using the default configuration of the Kafka client and we implemented all the code using synchronous programming to make the code more readable and easy to understand, as it is not the main goal of the project to use asynchronous programming.

Another problem that slowed down the performance of the application is probably the fact that kubernetes is running on a single machine which use Minikube, so the performance of the application is limited by the performance of the machine, as we also have containers running inside minikube container and this add another level of overhead.

## Acknowledgements


During the development of Piper-kt, we gained a deep understanding of microservices architecture and the associated challenges. We learned how to balance crucial aspects such as security, scalability, and usability in a real-time communication project.
The collaboration between different teams for the development of individual microservices and the management of communication between them through an event broker expanded our knowledge of best practices in distributed systems development.
Furthermore, the importance of evaluating software quality in terms of functionality, reliability, and performance provided us with a comprehensive overview of the challenges and opportunities in implementing a complex communication platform like Piper-kt.
Another challenge was the integration of previously developed microservices, which allow us to experiment and gain confidence in approaching real-world problems.
Furthermore we also find out the challenges of rewriting the microservices using different technologies and language, allowing us to rethink about the domain model and the overall structure, giving the project a big upgrade of the quality level.


## References

