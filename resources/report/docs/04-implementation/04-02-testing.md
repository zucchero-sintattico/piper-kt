# Implementation

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

You can find an example in the following code snippet (`servers-service` microservice):

```kotlin
const val PREFIX = "piperkt.services.servers"

class CleanArchitectureMultimediaTest : CleanArchitectureSpec(PREFIX)

class FrameworkIndependenceMultimediaTest : FrameworkIndependenceTest(PREFIX)
```

### Mocking and Stubbing

In each microservice, each layer is been tested with Unit / Integration tests.

## Mockito

With regard to the the application layer, in some microservice, we have used the [Mockito](https://site.mockito.org/) library to mock the dependencies of the _services_ like **repositories** and **event publishers**.

Using this technique, we can test the _services_ components in isolation, without the affect of the dependencies.
It also allows us to simulate, and then test, the services in different scenarios and edge cases, without the need of deploying the other components of the system.

You can find an example in the following code snippet (from the `servers-service` microservice):

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

### InMemory Mocking

As an alternative to the use of mocks, we tried also to use in-memory implementation for both the repositories and the event publishers.

```kotlin
open class InMemoryRepository<I : EntityId<*>, A : AggregateRoot<I>>(
    var entities: Map<I, A> = mapOf(),
) : Repository<I, A> {
    override fun findById(id: I): A? {
        return entities[id]
    }

    override fun save(entity: A) {
        entities += entity.id to entity
    }

    override fun deleteById(id: I): A? {
        val entity = findById(id)
        entities -= id
        return entity
    }

    fun clear() {
        entities = mapOf()
    }

    override fun update(entity: A) {
        entities += entity.id to entity
    }

    override fun deleteAll() {
        clear()
    }
}

open class MockedEventPublisher<E : DomainEvent> : EventPublisher<E> {
    val publishedEvents = mutableListOf<E>()

    override fun publish(event: E) {
        publishedEvents.add(event)
    }

    fun clear() {
        publishedEvents.clear()
    }
}

```

This simple implementations allowed us to test the services in a more realistic scenario, where the services are interacting with the repositories and the event publishers, without the need of mocking them.
This approach is based on data inside the repositories and the event publishers, and it's useful to test the services without mocking specific methods that are coupled with the implementation of the functionality in test.

```kotlin
class UserServiceTest :
    UnitTest.FunSpec({
        val userRepository = InMemoryUserRepository()
        val userEventPublisher = MockedUserEventPublisher()
        val userService = UserService(userRepository, userEventPublisher)

        val username = Username("username")
        val description = "description"
        val user =
            User(
                username = username,
                password = "password",
                description = description,
            )

        beforeEach {
            userRepository.clear()
            userEventPublisher.clear()
            userRepository.save(user)
        }

        test("getUser") { userService.getUser(username.value) shouldBe user }

        test("getUser throws UserNotFound") {
            assertThrows<UserNotFound> { userService.getUser("nonExistingUsername") }
        }

        test("updateUserDescription") {
            val newDescription = "newDescription"
            userService.updateUserDescription(username.value, newDescription)
            val updatedUser = userRepository.findByUsername(username.value)!!
            updatedUser.description shouldBe newDescription
            userEventPublisher.publishedEvents shouldBe
                listOf(UserUpdatedEvent(user.username.value, description = newDescription))
        }

        test("updateUserDescription throws UserNotFound") {
            assertThrows<UserNotFound> {
                userService.updateUserDescription("nonExistingUsername", "")
            }
        }

    })

```
