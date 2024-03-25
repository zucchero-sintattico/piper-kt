package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.matchers.shouldBe
import mocks.MockedSessionEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.application.usecases.CreateSession
import piperkt.services.multimedia.application.usecases.CreateSession.Command
import piperkt.services.multimedia.domain.events.SessionEvent.SessionCreated

class CreateSessionTest :
    Test.Unit.FunSpec({
        val sessionRepository = InMemorySessionRepository()
        val eventPublisher = MockedSessionEventPublisher()
        val createSession = CreateSession(sessionRepository, eventPublisher)

        beforeEach {
            eventPublisher.clear()
            sessionRepository.clear()
        }

        test("should create session and publish SessionCreated event") {
            val users = listOf(UsersData.john())
            val result = createSession(Command(users.map { it.username.value }))
            result shouldBe success()
            eventPublisher.publishedEvents shouldBe
                listOf(SessionCreated(sessionRepository.sessions.values.first().id, users))
        }
    })
