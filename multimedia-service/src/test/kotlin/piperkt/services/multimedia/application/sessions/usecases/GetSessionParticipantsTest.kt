package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData.jane
import data.UsersData.john
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.asSuccess
import piperkt.services.multimedia.application.usecases.GetSessionParticipants
import piperkt.services.multimedia.application.usecases.GetSessionParticipants.Query
import piperkt.services.multimedia.application.usecases.GetSessionParticipants.Response
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionId

class GetSessionParticipantsTest :
    Test.Unit,
    FunSpec({
        val sessionRepository = InMemorySessionRepository()
        val getSessionParticipants = GetSessionParticipants(sessionRepository)

        beforeEach { sessionRepository.clear() }

        test("should return users when session exists") {
            val users = setOf(john().id, jane().id)
            val session = SessionFactory.withParticipants(users, users)
            sessionRepository.save(session)
            val result = getSessionParticipants(Query(session.id))
            result shouldBe Response(users).asSuccess()
        }

        test("should return SessionNotFound error when session does not exist") {
            val fakeSessionId = SessionId("fakeSessionId")
            val result = getSessionParticipants(Query(fakeSessionId))
            result shouldBe SessionErrors.SessionNotFound(fakeSessionId).asFailure()
        }
    })
