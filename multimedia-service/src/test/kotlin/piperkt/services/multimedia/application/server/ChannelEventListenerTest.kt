package piperkt.services.multimedia.application.server

import base.UnitTest
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import mocks.publishers.MockedSessionEventPublisher
import mocks.repositories.InMemoryDirectRepository
import mocks.repositories.InMemoryServerRepository
import mocks.repositories.InMemorySessionRepository
import piperkt.common.events.ChannelEvent
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.application.session.SessionService.Command.CreateSession
import piperkt.services.multimedia.domain.server.Channel
import piperkt.services.multimedia.domain.server.ChannelId
import piperkt.services.multimedia.domain.server.Server
import piperkt.services.multimedia.domain.user.Username

class ChannelEventListenerTest :
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
        val channelEventListener = ChannelEventsListener(serverRepository, sessionService)

        afterEach {
            sessionRepository.clear()
            serverRepository.clear()
            directRepository.clear()
            sessionEventPublisher.clear()
        }

        test("should react to ChannelCreatedEvent") {
            // Given
            val server = Server(members = setOf(Username("owner")))
            serverRepository.save(server)

            val channelId = "channel-id"
            val event =
                ChannelEvent.ChannelCreatedEvent(channelId = channelId, serverId = server.id.value)

            // When
            channelEventListener.handle(event)

            // Then
            val serverWithChannel = serverRepository.findById(server.id)!!
            serverWithChannel.channels().size shouldBe 1
            val channel = serverWithChannel.channels().first()
            channel.id.value shouldBe channelId
            channel.sessionId shouldNotBe null

            val session = sessionRepository.findById(channel.sessionId)!!
            session.allowedUsers() shouldBe setOf(Username("owner"))
        }

        test("should react to ChannelDeletedEvent") {
            // Given
            val server = Server(members = setOf(Username("owner")))
            val session = sessionService.createSession(CreateSession(server.members()))
            val channel = Channel(id = ChannelId(), sessionId = session.id)
            server.addChannel(channel)
            serverRepository.save(server)

            val event =
                ChannelEvent.ChannelDeletedEvent(
                    channelId = channel.id.value,
                    serverId = server.id.value
                )

            // When
            channelEventListener.handle(event)

            // Then
            val serverWithoutChannel = serverRepository.findById(server.id)!!
            serverWithoutChannel.channels().size shouldBe 0
        }
    })
