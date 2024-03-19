package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import io.kotest.matchers.shouldBe
import mocks.MockedEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.sessions.usecases.DeleteSessionUseCase.Command
import piperkt.services.multimedia.application.sessions.usecases.DeleteSessionUseCase.Errors.SessionNotFound
import piperkt.services.multimedia.application.sessions.usecases.DeleteSessionUseCase.Events.SessionDeleted
import piperkt.services.multimedia.application.success

class DeleteSessionUseCaseTest :
    Test.Unit.FunSpec({
        val sessionRepository = InMemorySessionRepository()
        val eventPublisher = MockedEventPublisher()
        val deleteSessionUseCase = DeleteSessionUseCase(sessionRepository, eventPublisher)

        beforeEach {
            eventPublisher.clear()
            sessionRepository.clear()
        }

        test("should delete session and publish SessionDeleted event") {
            val session = sessionRepository.createSession(emptyList())
            val result = deleteSessionUseCase(Command(session.id.value))
            result shouldBe success()
            eventPublisher.publishedEvents shouldBe listOf(SessionDeleted(session.id))
        }

        test("should return SessionNotFound error") {
            val fakeSessionId = "fakeSessionId"
            val result = deleteSessionUseCase(Command(fakeSessionId))
            result shouldBe SessionNotFound(fakeSessionId).asFailure()
        }
    })
