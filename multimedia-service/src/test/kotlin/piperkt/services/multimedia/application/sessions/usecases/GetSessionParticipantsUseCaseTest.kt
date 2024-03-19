package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.matchers.shouldBe
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.asSuccess
import piperkt.services.multimedia.application.sessions.usecases.GetSessionParticipantsUseCase.Errors.SessionNotFound
import piperkt.services.multimedia.application.sessions.usecases.GetSessionParticipantsUseCase.Query
import piperkt.services.multimedia.application.sessions.usecases.GetSessionParticipantsUseCase.Response
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
            val result = getSessionParticipantsUseCase(Query(session.id.value))
            result shouldBe Response(users.map { it.username.value }.toSet()).asSuccess()
        }

        test("should return SessionNotFound error when session does not exist") {
            val fakeSessionId = SessionId("fakeSessionId")
            val result = getSessionParticipantsUseCase(Query(fakeSessionId.value))
            result shouldBe SessionNotFound(fakeSessionId.value).asFailure()
        }
    })
