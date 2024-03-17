package piperkt.services.multimedia.application.sessions

import base.Test
import data.SessionsData
import io.kotest.matchers.shouldBe
import mocks.repositories.SessionRepositoryMocks
import piperkt.services.multimedia.domain.sessions.SessionId

class GetSessionParticipantsUseCaseTest :
    Test.Offline({
        val session = SessionsData.simpleSession()
        val sessionRepository = SessionRepositoryMocks.fromSessions(session)
        val getSessionParticipantsUseCase = GetSessionParticipantsUseCase(sessionRepository)

        test("should return users when session exists") {
            val result =
                getSessionParticipantsUseCase.handle(
                    GetSessionParticipantsUseCase.Query(session.id.value)
                )

            result.isSuccess shouldBe true
            result.getOrNull()?.users shouldBe
                session.participants.map { it.username.value }.toSet()
        }

        test("should return SessionNotFound error when session does not exist") {
            val fakeSessionId = SessionId("fakeSessionId")
            val result =
                getSessionParticipantsUseCase.handle(
                    GetSessionParticipantsUseCase.Query(fakeSessionId.value)
                )

            result.isFailure shouldBe true
            result.exceptionOrNull() shouldBe
                GetSessionParticipantsUseCase.Errors.SessionNotFound(fakeSessionId.value)
        }
    })
