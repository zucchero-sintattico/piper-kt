package piperkt.services.multimedia.application.sessions

import base.Test
import data.SessionsData
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.sessions.SessionRepository

class GetSessionParticipantsUseCaseTest :
    Test.Unit.FunSpec({
        val session = SessionsData.simpleSession()
        val sessionRepository = mock<SessionRepository>()
        val getSessionParticipantsUseCase = GetSessionParticipantsUseCase(sessionRepository)

        test("should return users when session exists") {
            whenever(sessionRepository.findById(session.id)).thenReturn(session)
            val result =
                getSessionParticipantsUseCase.handle(
                    GetSessionParticipantsUseCase.Query(session.id.value)
                )
            verify(sessionRepository).findById(session.id)
            result.isSuccess shouldBe true
            result.getOrNull()?.users shouldBe
                session.participants.map { it.username.value }.toSet()
        }

        test("should return SessionNotFound error when session does not exist") {
            val fakeSessionId = SessionId("fakeSessionId")
            whenever(sessionRepository.findById(fakeSessionId)).thenReturn(null)
            val result =
                getSessionParticipantsUseCase.handle(
                    GetSessionParticipantsUseCase.Query(fakeSessionId.value)
                )
            verify(sessionRepository).findById(fakeSessionId)
            result.isFailure shouldBe true
            result.exceptionOrNull() shouldBe
                GetSessionParticipantsUseCase.Errors.SessionNotFound(fakeSessionId.value)
        }
    })
