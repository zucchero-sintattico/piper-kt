package piperkt.services.multimedia.interfaces.web.api

import base.Test
import data.SessionsData
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.asSuccess
import piperkt.services.multimedia.application.sessions.GetSessionParticipantsUseCase
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.interfaces.web.controllers.GetSessionParticipantsController

class GetSessionParticipantsControllerTest :
    Test.Unit.FunSpec({
        val session = SessionsData.simpleSession()
        val getSessionParticipantsUseCase = mock<GetSessionParticipantsUseCase>()
        val getUserInSessionApi = GetSessionParticipantsController(getSessionParticipantsUseCase)

        test("should return users when session exists") {
            whenever(
                    getSessionParticipantsUseCase.handle(
                        GetSessionParticipantsUseCase.Query(session.id.value)
                    )
                )
                .thenReturn(
                    GetSessionParticipantsUseCase.Response(
                            session.participants.map { it.username.value }.toSet()
                        )
                        .asSuccess()
                )
            val result = getUserInSessionApi.handle(session.id.value)
            result shouldBe
                GetSessionParticipantsApi.Response(
                    session.participants.map { it.username.value }.toSet()
                )
        }

        test("should return NotFound when session does not exist") {
            val fakeSessionId = SessionId("nonExistingSessionId")
            whenever(
                    getSessionParticipantsUseCase.handle(
                        GetSessionParticipantsUseCase.Query(fakeSessionId.value)
                    )
                )
                .thenReturn(
                    GetSessionParticipantsUseCase.Errors.SessionNotFound(fakeSessionId.value)
                        .asFailure()
                )
            assertThrows<GetSessionParticipantsUseCase.Errors.SessionNotFound> {
                getUserInSessionApi.handle(fakeSessionId.value)
            }
        }
    })
