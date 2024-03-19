package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.matchers.shouldBe
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.failure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.domain.sessions.SessionId

class GetSessionParticipantsUseCaseTest :
    Test.Unit.FunSpec({
        val sessionRepository = InMemorySessionRepository()
        val getSessionParticipantsUseCase = GetSessionParticipantsUseCase(sessionRepository)

        beforeEach { sessionRepository.clear() }

        test("should return users when session exists") {
            val users = listOf(UsersData.john(), UsersData.jane())
            val session = sessionRepository.createSession(users.map { it.username.value })
            sessionRepository.addParticipant(session.id, users[0])
            sessionRepository.addParticipant(session.id, users[1])
            val result =
                getSessionParticipantsUseCase.handle(
                    GetSessionParticipantsUseCase.Query(session.id.value)
                )
            result shouldBe
                success(
                    GetSessionParticipantsUseCase.Response(users.map { it.username.value }.toSet())
                )
        }

        test("should return SessionNotFound error when session does not exist") {
            val fakeSessionId = SessionId("fakeSessionId")
            val result =
                getSessionParticipantsUseCase.handle(
                    GetSessionParticipantsUseCase.Query(fakeSessionId.value)
                )
            result shouldBe
                failure(GetSessionParticipantsUseCase.Errors.SessionNotFound(fakeSessionId.value))
        }
    })
