package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.asSuccess
import piperkt.services.multimedia.application.usecases.GetSessionParticipants
import piperkt.services.multimedia.application.usecases.GetSessionParticipants.Errors.SessionNotFound
import piperkt.services.multimedia.application.usecases.GetSessionParticipants.Query
import piperkt.services.multimedia.application.usecases.GetSessionParticipants.Response
import piperkt.services.multimedia.domain.SessionId

class GetSessionParticipantsTest :
    Test.Unit,
    FunSpec({
        val sessionRepository = InMemorySessionRepository()
        val getSessionParticipants = GetSessionParticipants(sessionRepository)

        beforeEach { sessionRepository.clear() }

        test("should return users when session exists") {
            val users = listOf(UsersData.john(), UsersData.jane())
            val session = sessionRepository.createSession(users.map { it.username.value })
            sessionRepository.addParticipant(session.id, users[0])
            sessionRepository.addParticipant(session.id, users[1])
            val result = getSessionParticipants(Query(session.id.value))
            result shouldBe Response(users.map { it.username.value }.toSet()).asSuccess()
        }

        test("should return SessionNotFound error when session does not exist") {
            val fakeSessionId = SessionId("fakeSessionId")
            val result = getSessionParticipants(Query(fakeSessionId.value))
            result shouldBe SessionNotFound(fakeSessionId.value).asFailure()
        }
    })
