package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mocks.MockedSessionEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.application.usecases.DeleteSession
import piperkt.services.multimedia.application.usecases.DeleteSession.Command
import piperkt.services.multimedia.application.usecases.DeleteSession.Errors.SessionNotFound
import piperkt.services.multimedia.domain.events.SessionEvent.SessionDeleted

class DeleteSessionTest :
    Test.Unit,
    FunSpec({
        val sessionRepository = InMemorySessionRepository()
        val eventPublisher = MockedSessionEventPublisher()
        val deleteSession = DeleteSession(sessionRepository, eventPublisher)

        beforeEach {
            eventPublisher.clear()
            sessionRepository.clear()
        }

        test("should delete session and publish SessionDeleted event") {
            val session = sessionRepository.createSession(emptyList())
            val result = deleteSession(Command(session.id.value))
            result shouldBe success()
            eventPublisher.publishedEvents shouldBe listOf(SessionDeleted(session.id))
        }

        test("should return SessionNotFound error") {
            val fakeSessionId = "fakeSessionId"
            val result = deleteSession(Command(fakeSessionId))
            result shouldBe SessionNotFound(fakeSessionId).asFailure()
        }
    })
