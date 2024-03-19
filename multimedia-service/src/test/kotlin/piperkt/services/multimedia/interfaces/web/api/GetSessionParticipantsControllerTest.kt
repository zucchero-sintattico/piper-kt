package piperkt.services.multimedia.interfaces.web.api

import base.Test
import data.UsersData.jane
import data.UsersData.john
import io.kotest.matchers.shouldBe
import mocks.repositories.InMemorySessionRepository
import org.junit.jupiter.api.assertThrows
import piperkt.services.multimedia.application.sessions.usecases.GetSessionParticipantsUseCase
import piperkt.services.multimedia.application.sessions.usecases.GetSessionParticipantsUseCase.Errors.SessionNotFound
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.interfaces.web.api.GetSessionParticipantsApi.Response
import piperkt.services.multimedia.interfaces.web.controllers.GetSessionParticipantsController

class GetSessionParticipantsControllerTest :
    Test.Unit.FunSpec({
        val sessionRepository = InMemorySessionRepository()
        val getUserInSessionApi =
            GetSessionParticipantsController(GetSessionParticipantsUseCase(sessionRepository))

        beforeEach { sessionRepository.clear() }

        test("should return users when session exists") {
            val users = listOf(john(), jane())
            val session = sessionRepository.createSession(users.map { it.username.value })
            sessionRepository.addParticipant(session.id, john())
            val result = getUserInSessionApi(session.id.value)
            result shouldBe Response(setOf(john().username.value))
        }

        test("should throw SessionNotFound when session does not exist") {
            val fakeSessionId = SessionId("nonExistingSessionId")
            assertThrows<SessionNotFound> { getUserInSessionApi(fakeSessionId.value) }
        }
    })
