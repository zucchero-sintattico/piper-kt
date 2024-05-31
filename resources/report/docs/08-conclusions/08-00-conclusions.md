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

## Acknowledgements

## References
