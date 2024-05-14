package piperkt.services.multimedia.application.direct

import base.UnitTest
import data.UsersData.jane
import data.UsersData.john
import io.kotest.matchers.shouldBe
import mocks.publishers.MockedSessionEventPublisher
import mocks.repositories.InMemoryDirectRepository
import mocks.repositories.InMemoryServerRepository
import mocks.repositories.InMemorySessionRepository
import piperkt.common.events.FriendshipEvent
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.domain.direct.DirectId

class DirectEventListenerTest :
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
        val directEventListener = DirectEventsListener(directRepository, sessionService)

        afterEach {
            sessionRepository.clear()
            serverRepository.clear()
            directRepository.clear()
            sessionEventPublisher.clear()
        }

        test("should react to FriendshipRequestAcceptedEvent") {
            // Given
            val event =
                FriendshipEvent.FriendshipRequestAcceptedEvent(
                    fromUser = john().id.value,
                    toUser = jane().id.value
                )

            // When
            directEventListener.handle(event)

            // Then
            val direct = directRepository.findById(DirectId(setOf(john().id, jane().id)))!!
            val session = sessionRepository.findById(direct.sessionId)!!
            session.allowedUsers() shouldBe setOf(john().id, jane().id)
        }
    })
