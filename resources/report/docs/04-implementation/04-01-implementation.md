# Implementation

## Microservices Implementation

### Domain Layer

The domain layer is the core of the application, it contains the entities, value objects, factories, aggregates, and repositories.
Each microservice works directly with the **aggregates** (composed of entities and value objects), which serve as the entry points for the various operations, and are the minimum serializable unit then on the databases.
You can find an example in the following code snippet (from the Servers-microservice):

```kotlin
// Channel Entity
data class Channel(
    override val id: ChannelId = ChannelId(),
    var name: String,
    val type: ChannelType,
    var description: String,
    val messages: MutableList<Message> = mutableListOf(),
) : Entity<ChannelId>(id) {

    fun addMessage(message: Message) {
        messages.add(message)
    }
}
// Server Aggregate, composed by Channel entities
class Server(
    override val id: ServerId = ServerId(),
    var name: String,
    var description: String = "",
    val owner: String,
    var users: MutableList<String> = mutableListOf(owner),
    var channels: MutableList<Channel> = mutableListOf(),
) : AggregateRoot<ServerId>(id) {
    fun addUser(username: String) {
        this.users.add(username)
    }

    fun removeUser(username: String) {
        this.users.remove(username)
    }

    fun addChannel(channel: Channel) {
        this.channels.add(channel)
    }

    fun removeChannel(channel: Channel) {
        this.channels.remove(channel)
    }

    fun isUserAdmin(username: String): Boolean {
        return this.owner == username
    }
}
```

### Application Layer

In this layer, we have the **Services**, which are responsible for orchestrating the business logic.
They are the entry points for controllers and are responsible for validating input, executing domain operations, calling event publisher methods, and repositories for data persistence.

Each Service, has its own Api and works with the concept of Requests and Responses, which are simple data classes that represent the input and output of the service methods.

```kotlin
// Example of Server command operations request and response hierarchy
sealed interface ServerCommand {
    sealed interface DeleteServer : ServerCommand {
        data class Request(val serverId: ServerId, override val requestFrom: String) :
            DeleteServer, ServiceRequest

        data class Response(val serverId: ServerId) : DeleteServer
    }
    // Other commands...
}
```

The error handling is done through the Result class, which is a simple wrapper that can contain a success value or a failure value. In the case of a failure, the Result contains an ad-hoc exception that represents the error.

```kotlin
// Example of Server service delete operation
open class ServerService(
    private val serverRepository: ServerRepository,
    private val eventPublisher: ServerEventPublisher,
) : ServerServiceApi {
    override fun deleteServer(
        request: ServerCommand.DeleteServer.Request,
    ): Result<ServerCommand.DeleteServer.Response> {
        val server = serverRepository.findById(request.serverId)
        return if (server != null) {
            if (server.isUserAdmin(request.requestFrom)) {
                serverRepository.deleteById(request.serverId)
                eventPublisher.publish(ServerDeletedEvent(request.serverId.value))
                // The success response
                Result.success(ServerCommand.DeleteServer.Response(request.serverId))
            } else {
                // The failure response
                Result.failure(ServerServiceException.UserNotHasPermissionsException())
            }
        } else {
            // Another failure response
            Result.failure(ServerServiceException.ServerNotFoundExceptionException())
        }
    }
    // Other methods...
}
```

In this level, we also have declared the **Repositories** interfaces, which are then implemented in the infrastructure level.
**TOFINISH**

### Infrastructure Layer

In this layer, we have the implementations of the repositories, event publishers, and other infrastructure components.

#### Persistence

The persistence package contains the db model and the repositories implementations.
In the _model_ subpackage, we have the db entities, which are the classes that represent the tables in the database and their respective repository.
In the _repositories_ subpackage, we have the implementations of the repositories interfaces defined in the domain layer which use the db-entities repository to implement the operations

With this approach we separate the domain representation from the db one, so that the domain layer is completely independent from the technology used for the persistence.

````kotlin
// model/UserEntity.kt
@MappedEntity
data class UserEntity(
    @Id @GeneratedValue val id: String? = null,
    val username: String,
    val password: String,
    val email: String? = null,
    val description: String? = null,
    val profilePicture: String? = null,
    val refreshToken: String? = null,
)

@MongoRepository
interface UserEntityRepository : GenericRepository<UserEntity, String> {

    fun findAll(): List<UserEntity>

    fun findByUsername(username: String): UserEntity?

    fun findByRefreshToken(refreshToken: String): UserEntity?

    fun deleteByUsername(username: String)

    fun save(entity: UserEntity)

    fun updateByUsername(username: String, entity: UserEntity)

    fun deleteAll()
}
```

```kotlin
// repositories/UserRepositoryImpl.kt
@Singleton
class UserRepositoryImpl(private val userEntityRepository: UserEntityRepository) : UserRepository {
    override fun findAll(): List<User> {
        return userEntityRepository.findAll().map { it.toDomain() }
    }

    override fun findByUsername(username: String): User? {
        return userEntityRepository.findByUsername(username)?.toDomain()
    }

    override fun findByRefreshToken(refreshToken: String): User? {
        return userEntityRepository.findByRefreshToken(refreshToken)?.toDomain()
    }

    override fun findById(id: Username): User? {
        return userEntityRepository.findByUsername(id.value)?.toDomain()
    }

    override fun save(entity: User) {
        userEntityRepository.save(entity.toEntity())
    }

    override fun deleteById(id: Username): User? {
        val user = findById(id)
        userEntityRepository.deleteByUsername(id.value)
        return user
    }

    override fun update(entity: User) {
        val user = userEntityRepository.findByUsername(entity.username.value)
        userEntityRepository.updateByUsername(user!!.username, entity.toEntity(user.id))
    }

    override fun deleteAll() {
        userEntityRepository.deleteAll()
    }
}
```

This package also contain the mapping between the domain entities and the db entities, which is done through the `toDomain()` and `toEntity()` extension functions.

```kotlin
object UserEntityMapper {
    /** Convert a user entity to a user. */
    fun UserEntity.toDomain() =
        User(
            username = Username(username),
            password = password,
            email = email,
            description = description,
            profilePicture = profilePicture,
            refreshToken = refreshToken
        )

    /** Convert a user to a user entity. */
    fun User.toEntity(actualId: String? = null) =
        UserEntity(
            id = actualId,
            username = username.value,
            password = password,
            email = email,
            description = description,
            profilePicture = profilePicture,
            refreshToken = refreshToken
        )
}
```

#### Events

In this package, we simply defined the Kafka implementation of the event publishers, which are responsible for publishing the events to the Kafka topic.

You can find an example in the following code snippet (from the Servers-microservice):

```kotlin
@KafkaClient
interface KafkaServerEventPublisher {

    @Topic(ServerCreatedEvent.TOPIC) fun publish(event: ServerCreatedEvent)

    @Topic(ServerDeletedEvent.TOPIC) fun publish(event: ServerDeletedEvent)

    @Topic(ServerUpdatedEvent.TOPIC) fun publish(event: ServerUpdatedEvent)

    @Topic(ServerUserAddedEvent.TOPIC) fun publish(event: ServerUserAddedEvent)

    @Topic(ServerUserRemovedEvent.TOPIC) fun publish(event: ServerUserRemovedEvent)

    @Topic(ServerUserKickedEvent.TOPIC) fun publish(event: ServerUserKickedEvent)
}

@Singleton
class ServerEventPublisherImpl(private val kafkaServerEventPublisher: KafkaServerEventPublisher) :
    ServerEventPublisher {
    override fun publish(event: ServerEvent) {
        when (event) {
            is ServerCreatedEvent -> kafkaServerEventPublisher.publish(event)
            is ServerDeletedEvent -> kafkaServerEventPublisher.publish(event)
            is ServerUpdatedEvent -> kafkaServerEventPublisher.publish(event)
            is ServerUserAddedEvent -> kafkaServerEventPublisher.publish(event)
            is ServerUserRemovedEvent -> kafkaServerEventPublisher.publish(event)
            is ServerUserKickedEvent -> kafkaServerEventPublisher.publish(event)
        }
    }
}
```

#### Implementation

This package contains the implementation of the services defined in the application layer, which use the @Singleton annotation to be managed by the Micronaut framework that allow to inject the dependencies where needed.
It also contains the implementation of the event listeners that are defined in the application layer and are used to listen to the events published by the services and execute the necessary operations.

```kotlin
object Services {
    @Singleton
    class UserServiceImpl(userRepository: UserRepository, userEventPublisher: UserEventPublisher) :
        UserService(userRepository, userEventPublisher)

    @Singleton
    class AuthServiceImpl(userRepository: UserRepository, userEventPublisher: UserEventPublisher) :
        AuthService(userRepository, userEventPublisher)
}

object EventsListeners {
    @Singleton
    class ServerEventListenerService(
        serverRepository: ServerRepository,
        sessionService: SessionService
    ) : ServerEventsListener(serverRepository, sessionService)

    @Singleton
    class DirectEventListenerService(
        directRepository: DirectRepository,
        sessionService: SessionService
    ) : DirectEventsListener(directRepository, sessionService)

    @Singleton
    class ChannelEventListenerService(
        serverRepository: ServerRepository,
        sessionService: SessionService
    ) : ChannelEventsListener(serverRepository, sessionService)
}
```

### Interfaces Layer

In this layer, we have the http controllers, which are responsible for receiving the requests from the clients, calling the services to execute the business logic, and returning the http responses to the client.

Every controller has its own Api, which is composed of the request and response data classes, and each controller methods is defined for a specific endpoint.

```kotlin
// Example of Server http controller API
@Secured(SecurityRule.IS_AUTHENTICATED)
interface ServerHttpControllerApi {

    // This annotation is used to generate the OpenAPI documentation of the endpoint
    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Server created successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
    )
    @Post("/servers")
    fun createServer(
        @Body request: ServerApi.CreateServerApi.Request,
        principal: Principal
    ): ServerApi.CreateServerApi.Response

    // Other methods...
}

// Http controller implementation
@Controller
class ServerHttpController(private val serverService: ServerService) : ServerHttpControllerApi {

    override fun createServer(
        request: ServerApi.CreateServerApi.Request,
        principal: Principal,
    ): ServerApi.CreateServerApi.Response {
        val response =
            serverService
                .createServer(
                    ServerCommand.CreateServer.Request(
                        name = request.name,
                        description = request.description,
                        requestFrom = principal.name
                    )
                )
                .getOrThrow()
        return ServerApi.CreateServerApi.Response(serverId = response.serverId.value)
    }
    // Other methods...
}
````

The controllers are very simple, they only call the services methods, and then return the responses to the client.

Regarding error handling, as you can notice, we are using the `getOrThrow()` method, which is an extension function of the Result class, that throws the exception contained in the Result in case of a failure.

Thanks to Micronaut Framework, we can easily define a specific error handler once, and then it will be used for all the controllers in the application.

```kotlin
@Produces
@Singleton
@Requires(
    classes =
        [ServerServiceException.ServerNotFoundExceptionException::class, ExceptionHandler::class]
)
class ServerNotFound :
    ExceptionHandler<ServerServiceException.ServerNotFoundExceptionException, ErrorResponse> {
    @Error(
        global = true,
        exception = ServerServiceException.ServerNotFoundExceptionException::class,
    )
    @Status(HttpStatus.NOT_FOUND)
    override fun handle(
        request: HttpRequest<*>?,
        exception: ServerServiceException.ServerNotFoundExceptionException?
    ): ErrorResponse {
        return ErrorResponse(exception!!.message)
    }
}
```

In this case, we defined a specific error handler for the `ServerNotFoundExceptionException`, which returns a `NOT_FOUND` (404) status code to the client.

### Presentation Layer

In this layer, we have the mappers, which are responsible for converting the domain entities to the response data classes, and vice versa.

### Configuration Layer

In this layer, we have the configuration files, which are used to configure the application using Micronaut properties.

```kotlin
// Example of the SocketServer configuration
@ConfigurationProperties("socketio")
class SocketIOConfiguration {
    var port: Int = Random.nextInt(MIN_RANDOM_PORT, MAX_RANDOM_PORT)

    companion object {
        const val MIN_RANDOM_PORT = 10000
        const val MAX_RANDOM_PORT = 20000
    }
}
```

This allow to define the configuration in a single place, and then inject the configuration properties where needed, as in the following example:

```kotlin
open class MultimediaSocketIOServer(
    private val sessionService: SessionService,
    private val objectMapper: JsonMapper,
    val configuration: SocketIOConfiguration,
)
```

Using this approach, we can easily change the configuration of the application without affecting the rest of the code, just by modifiying the configuration file that Micronaut will automatically parse and create the configuration beans ready to be injected.

```yaml
application:
  name: 'Piperkt Users Service'
  version: '1.0.0'
---
socketio:
  port: 8082
```
