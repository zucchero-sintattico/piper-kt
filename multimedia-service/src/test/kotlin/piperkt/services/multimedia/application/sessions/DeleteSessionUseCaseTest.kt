package piperkt.services.multimedia.application.sessions

import base.Test
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import piperkt.services.multimedia.application.EventPublisher
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.domain.sessions.SessionRepository

class DeleteSessionUseCaseTest :
    Test.Unit.FunSpec({
        val sessionRepository = mock<SessionRepository>()
        val eventPublisher = mock<EventPublisher>()
        val deleteSessionUseCase = DeleteSessionUseCase(sessionRepository, eventPublisher)

        val sessionId = "randomId"

        test("should delete session and publish SessionDeleted event") {
            whenever(sessionRepository.exists(sessionId)).thenReturn(true)
            val result = deleteSessionUseCase.handle(DeleteSessionUseCase.Command(sessionId))
            result shouldBe success()
            verify(sessionRepository).deleteSession(sessionId)
            verify(eventPublisher).publish(DeleteSessionUseCase.Events.SessionDeleted(sessionId))
        }

        test("should return SessionNotFound error") {
            whenever(sessionRepository.exists(sessionId)).thenReturn(false)
            val result = deleteSessionUseCase.handle(DeleteSessionUseCase.Command(sessionId))
            result shouldBe DeleteSessionUseCase.Errors.SessionNotFound(sessionId).asFailure()
        }
    })
