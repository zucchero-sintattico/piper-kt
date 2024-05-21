package piperkt.services.multimedia.application.server

import base.UnitTest
import io.kotest.matchers.shouldBe
import mocks.publishers.MockedSessionEventPublisher
import mocks.repositories.InMemoryDirectRepository
import mocks.repositories.InMemoryServerRepository
import mocks.repositories.InMemorySessionRepository
import piperkt.events.*
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.domain.server.Server
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.user.Username

class ServerEventListenerTest :
    UnitTest.FunSpec({
        val sessionRepository = InMemorySessionRepository()
        val serverRepository = InMemoryServerRepository()
        val directRepository = InMemoryDirectRepository()
        val sessionEventPublisher = MockedSessionEventPublisher()
        val sessionService =
            SessionService(
                sessionRepository,
                serverRepository,
                directRepository,
                sessionEventPublisher
            )
        val serverEventListener = ServerEventsListener(serverRepository, sessionService)

        afterEach {
            sessionRepository.clear()
            serverRepository.clear()
            directRepository.clear()
            sessionEventPublisher.clear()
        }

        test("should react to ServerCreatedEvent") {
            // Given
            val event = ServerCreatedEvent(serverId = "server-id", owner = "owner")

            // When
            serverEventListener.handle(event)

            // Then
            val server = serverRepository.findById(ServerId("server-id"))!!
            server.members() shouldBe setOf(Username("owner"))
        }

        test("should react to ServerDeletedEvent") {
            // Given
            val event = ServerDeletedEvent(serverId = "server-id")
            serverRepository.save(Server(ServerId("server-id"), setOf(Username("owner"))))

            // When
            serverEventListener.handle(event)

            // Then
            serverRepository.findById(ServerId("server-id")) shouldBe null
        }

        test("should react to ServerUserAddedEvent") {
            // Given
            val event = ServerUserAddedEvent(serverId = "server-id", username = "user")

            serverRepository.save(Server(ServerId("server-id"), setOf(Username("owner"))))

            // When
            serverEventListener.handle(event)

            // Then
            val server = serverRepository.findById(ServerId("server-id"))!!
            server.members() shouldBe setOf(Username("owner"), Username("user"))

            server.channels().forEach {
                val session = sessionRepository.findById(it.sessionId)!!
                session.allowedUsers() shouldBe setOf(Username("owner"), Username("user"))
            }
        }

        test("should react to ServerUserRemovedEvent") {
            // Given
            val event = ServerUserRemovedEvent(serverId = "server-id", username = "user")

            serverRepository.save(
                Server(ServerId("server-id"), setOf(Username("owner"), Username("user")))
            )

            // When
            serverEventListener.handle(event)

            // Then
            val server = serverRepository.findById(ServerId("server-id"))!!
            server.members() shouldBe setOf(Username("owner"))

            server.channels().forEach {
                val session = sessionRepository.findById(it.sessionId)!!
                session.allowedUsers() shouldBe setOf(Username("owner"))
            }
        }

        test("should react to ServerUserKickedEvent") {
            // Given
            val event = ServerUserKickedEvent(serverId = "server-id", username = "user")

            serverRepository.save(
                Server(ServerId("server-id"), setOf(Username("owner"), Username("user")))
            )

            // When
            serverEventListener.handle(event)

            // Then
            val server = serverRepository.findById(ServerId("server-id"))!!
            server.members() shouldBe setOf(Username("owner"))

            server.channels().forEach {
                val session = sessionRepository.findById(it.sessionId)!!
                session.allowedUsers() shouldBe setOf(Username("owner"))
            }
        }
    })
