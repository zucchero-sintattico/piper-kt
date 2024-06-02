# Experiments

We choose to use this project as an opportunity to experiment with different software architecture patterns and techniques.
In fact the overall architecture of each microservice is the same, following the Clean Architecture, but we have experimented with different approaches to the implementation of the application and domain layers.
For example some of them use the Result as a return type for the methods, while others use exceptions to handle errors, some of them use defined Command and Query classes to handle the requests, while others use plain parameters.

We also tried implementing an application layer defining single use case classes, but we found that it was not necessary for the complexity of the project, so we decided to use the service classes as the application layer.
