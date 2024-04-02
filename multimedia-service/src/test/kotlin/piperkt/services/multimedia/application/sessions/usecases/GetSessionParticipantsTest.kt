package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.asSuccess
import piperkt.services.multimedia.application.usecases.GetSessionParticipants
import piperkt.services.multimedia.application.usecases.GetSessionParticipants.Query
import piperkt.services.multimedia.application.usecases.GetSessionParticipants.Response
import piperkt.services.multimedia.domain.session.Session
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionId

class GetSessionParticipantsTest :
    Test.Unit,
    FunSpec({
        val sessionRepository = InMemorySessionRepository()
        val getSessionParticipants = GetSessionParticipants(sessionRepository)

        beforeEach { sessionRepository.clear() }

        test("should return users when session exists") {
            val users = listOf(UsersData.john(), UsersData.jane()).map { it.id }
            val sessionId = SessionId("sessionId")
            val session = Session(id = sessionId, allowedUsers = users, participants = users)
            sessionRepository.save(session)
            val result = getSessionParticipants(Query(sessionId))
            result shouldBe Response(users.toSet()).asSuccess()
        }

        test("should return SessionNotFound error when session does not exist") {
            val fakeSessionId = SessionId("fakeSessionId")
            val result = getSessionParticipants(Query(fakeSessionId))
            result shouldBe SessionErrors.SessionNotFound(fakeSessionId).asFailure()
        }
    })
