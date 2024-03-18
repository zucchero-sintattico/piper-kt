package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import io.kotest.matchers.shouldBe
import mocks.MockedEventPublisher
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.sessions.SessionRepository

class DeleteSessionUseCaseTest :
    Test.Unit.FunSpec({
        val sessionRepository = mock<SessionRepository>()
        val eventPublisher = MockedEventPublisher()
        val deleteSessionUseCase = DeleteSessionUseCase(sessionRepository, eventPublisher)

        val sessionId = "randomId"

        beforeEach { eventPublisher.clear() }

        test("should delete session and publish SessionDeleted event") {
            whenever(sessionRepository.exists(sessionId)).thenReturn(true)
            val result = deleteSessionUseCase.handle(DeleteSessionUseCase.Command(sessionId))
            result shouldBe success()
            verify(sessionRepository).deleteSession(sessionId)
            eventPublisher.publishedEvents shouldBe
                listOf(DeleteSessionUseCase.Events.SessionDeleted(SessionId(sessionId)))
        }

        test("should return SessionNotFound error") {
            whenever(sessionRepository.exists(sessionId)).thenReturn(false)
            val result = deleteSessionUseCase.handle(DeleteSessionUseCase.Command(sessionId))
            result shouldBe DeleteSessionUseCase.Errors.SessionNotFound(sessionId).asFailure()
        }
    })
