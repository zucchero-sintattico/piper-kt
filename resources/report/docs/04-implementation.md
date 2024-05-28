# Implementation

## Microservices Implementation

### Domain Level

The domain level is the core of the application, it contains the entities, value objects, factories, aggregates, and repositories.
Each microservice works directly with the **aggregates** (composed of entities and value objects), which serve as the entry points for the various operations, and are the minimum serializable unit then on the databases.
You can find an example in the following code snippet:

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

### Application Level

In this level, we have the **Services**, which are responsible for orchestrating the business logic.
They are the entry points for controllers and are responsible for validating input, executing domain operations, calling event posting methods, and repositories for data persistence.

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

## Testing

### Architecture Testing

In order to enforce the architecture constraints, we have implemented some architecture tests, from the very beginning of the development phase.

In these test, we check if the dependencies between the modules are correct, and if the modules are respecting the architecture constraints. 
The tests are defined in the `architecture-tests` submodule, where we can find the `ArchitectureSpec` abstract class, which contains some helper methods to define the architecture tests:

```kotlin
abstract class ArchitectureSpec(val prefix: String) : AnnotationSpec() {

    fun assertLayer(name: String): Layer {
        return Layer(name, "$prefix.$name..")
    }

    fun assertArchitecture(block: DependencyRules.() -> Unit) {
        Konsist.scopeFromProject().assertArchitecture { block() }
    }

    fun assertPackageDoesNotDependOnFrameworks(
        packageName: String,
        frameworks: List<String> = emptyList()
    ) {
        Konsist.scopeFromPackage("$prefix.$packageName..").files.forEach { file ->
            val dependencies = file.imports.map { it.name }
            val forbidden =
                dependencies.filter { dependency ->
                    frameworks.any { framework -> dependency.startsWith(framework) }
                }
            forbidden shouldBe emptyList()
        }
    }
}
```

After this, we have defined two classes, `CleanArchitectureSpec`and `FrameworkIndependenceTest` that extend the `ArchitectureSpec` class, and define the architecture tests for a generic package.

In the first one, we define the Clean Architecture constraints, where the domain layer doesn't depend on any other layer, the application layer depends on the domain layer, the interfaces layer depends on the application and domain layers as well as the interfaces layer:
```kotlin
abstract class CleanArchitectureSpec(prefix: String) : ArchitectureSpec(prefix) {
    private val domainLayer = assertLayer("domain")
    private val applicationLayer = assertLayer("application")
    private val interfacesLayer = assertLayer("interfaces")
    private val infrastructureLayer = assertLayer("infrastructure")

    @Test
    fun `architecture is Clean`() {
        assertArchitecture {
            domainLayer.dependsOnNothing()
            applicationLayer.dependsOn(domainLayer)
            interfacesLayer.dependsOn(applicationLayer, domainLayer)
            infrastructureLayer.dependsOn(applicationLayer, domainLayer)
        }
    }
}
```

In the second one, we simply check if the domain layer and and the application layer don't depend on any framework:
```kotlin
abstract class FrameworkIndependenceTest(prefix: String) : ArchitectureSpec(prefix) {

    private val frameworks = listOf("io.micronaut", "jakarta")

    @Test
    fun `domain layer doesn't depend on frameworks`() {
        assertPackageDoesNotDependOnFrameworks("domain", frameworks)
    }

    @Test
    fun `application layer doesn't depend on frameworks`() {
        assertPackageDoesNotDependOnFrameworks("application", frameworks)
    }
}
```

Each microservice submodule, simply extends these classes, passing them the package prefix.

You can find an example in the following code snippet (Servers microservice):

```kotlin
const val PREFIX = "piperkt.services.servers"

class CleanArchitectureMultimediaTest : CleanArchitectureSpec(PREFIX)

class FrameworkIndependenceMultimediaTest : FrameworkIndependenceTest(PREFIX)
```

### Mocking and Stubbing

In each microservice, each layer is been tested with Unit / Integration tests.

With regard to the the application layer, we have used the [Mockito](https://site.mockito.org/) library to mock the dependencies of the *services* like repositories and event publishers.

Using this technique, we can test the *services* components in isolation, without the affect of the dependencies.
It also allows us to simulate, and then test, the services in different scenarios and edge cases, without the need of deploying the other components of the system.

You can find an example in the following code snippet (from the Servers microservice):

```kotlin
// Basic Server Service Test
open class BasicServerServiceTest : UnitTest() {
    // Mocks
    val serverRepository = mock<ServerRepository>()
    val eventPublisher = mock<ServerEventPublisher>()
    val serverService = ServerService(serverRepository, eventPublisher)
}

// Actual Server Service command operations test class
class ServerServiceCommandTest : BasicServerServiceTest() {

    @BeforeEach
    fun setUp() {
        reset(serverRepository, eventPublisher)
    }

    @Test
    fun `should not allow to update a server if is not the admin`() {
        // Mocks of the repository return value
        whenever(serverRepository.findById(any())).thenReturn(simpleServer)
        serverService.updateServer(
            ServerCommand.UpdateServer.Request(
                simpleServerId,
                "serverName",
                "serverDescription",
                "member"
            )
        ) shouldBe Result.failure(ServerServiceException.UserNotHasPermissionsException())
        // Verify if the event publisher was not called
        verifyNoInteractions(eventPublisher)
    }
    // Other tests...
}
```
