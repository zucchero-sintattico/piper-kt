package piperkt.services.multimedia.interfaces.web.api

import base.Test
import data.SessionsData
import io.kotest.matchers.shouldBe
import mocks.repositories.SessionRepositoryMocks
import org.junit.jupiter.api.assertThrows
import piperkt.services.multimedia.application.sessions.GetSessionParticipantsUseCase
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.interfaces.web.controllers.GetSessionParticipantsController

class GetSessionParticipantsControllerTest :
    Test.Unit.FunSpec({
        val session = SessionsData.simpleSession()
        val sessionRepository = SessionRepositoryMocks.fromSessions(session)
        val getUserInSessionUseCase = GetSessionParticipantsUseCase(sessionRepository)
        val getUserInSessionApi = GetSessionParticipantsController(getUserInSessionUseCase)

        test("should return users when session exists") {
            val result = getUserInSessionApi.handle(session.id.value)
            result shouldBe
                GetSessionParticipantsApi.Response(
                    session.participants.map { it.username.value }.toSet()
                )
        }

        test("should return NotFound when session does not exist") {
            val fakeSessionId = SessionId("nonExistingSessionId")
            assertThrows<GetSessionParticipantsUseCase.Errors.SessionNotFound> {
                getUserInSessionApi.handle(fakeSessionId.value)
            }
        }
    })
