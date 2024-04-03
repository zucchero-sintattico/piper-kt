package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mocks.publishers.MockedSessionEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.application.usecases.internal.DeleteSession
import piperkt.services.multimedia.application.usecases.internal.DeleteSession.Command
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionEvent.SessionDeleted
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionId

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
            val session = SessionFactory.empty()
            sessionRepository.save(session)
            val result = deleteSession(Command(session.id))
            result shouldBe success()
            eventPublisher.publishedEvents shouldBe listOf(SessionDeleted(session.id))
        }

        test("should return SessionNotFound error") {
            val fakeSessionId = SessionId("fakeSessionId")
            val result = deleteSession(Command(fakeSessionId))
            result shouldBe SessionErrors.SessionNotFound(fakeSessionId).asFailure()
        }
    })
