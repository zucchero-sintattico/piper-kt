package piperkt.services.multimedia.interfaces.web.controllers

import base.UnitTest
import data.UsersData.jane
import data.UsersData.john
import io.kotest.matchers.shouldBe
import mocks.repositories.InMemorySessionRepository
import org.junit.jupiter.api.assertThrows
import piperkt.services.multimedia.application.usecases.GetSessionParticipants
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.interfaces.web.api.GetSessionParticipantsApi

class GetSessionParticipantsControllerTest :
    UnitTest.FunSpec({
        val sessionRepository = InMemorySessionRepository()
        val getUserInSessionApi =
            GetSessionParticipantsController(GetSessionParticipants(sessionRepository))

        beforeEach { sessionRepository.clear() }

        test("should return users when session exists") {
            val users = setOf(john().id, jane().id)
            val session = SessionFactory.fromAllowedParticipants(users)
            sessionRepository.save(session)
            val result = getUserInSessionApi(session.id.value)
            result shouldBe GetSessionParticipantsApi.Response(users.map { it.value }.toSet())
        }

        test("should throw SessionNotFound when session does not exist") {
            val fakeSessionId = SessionId("nonExistingSessionId")
            assertThrows<SessionErrors.SessionNotFound> { getUserInSessionApi(fakeSessionId.value) }
        }
    })
