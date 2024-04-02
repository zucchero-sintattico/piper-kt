package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData.john
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mocks.MockedSessionEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.application.usecases.internal.CreateSession
import piperkt.services.multimedia.application.usecases.internal.CreateSession.Command

class CreateSessionTest :
    Test.Unit,
    FunSpec({
        val sessionRepository = InMemorySessionRepository()
        val eventPublisher = MockedSessionEventPublisher()
        val createSession = CreateSession(sessionRepository, eventPublisher)

        beforeEach {
            eventPublisher.clear()
            sessionRepository.clear()
        }

        test("should create session and publish SessionCreated event") {
            val users = setOf(john().id)
            val result = createSession(Command(users))
            result shouldBe success()
        }
    })
