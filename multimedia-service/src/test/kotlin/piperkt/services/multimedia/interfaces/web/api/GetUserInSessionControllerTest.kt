package piperkt.services.multimedia.interfaces.web.api

import base.MicronautTest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.assertThrows
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.asSuccess
import piperkt.services.multimedia.application.sessions.GetUsersInSessionUseCase
import piperkt.services.multimedia.domain.sessions.SessionId

class GetUserInSessionControllerTest :
    MicronautTest({
        val getUserInSessionUseCase: GetUsersInSessionUseCase = mock()
        val getUserInSessionApi = GetUserInSessionController(getUserInSessionUseCase)

        test("should return users when session exists") {
            val sessionId = SessionId("sessionId")
            val users = setOf("user1", "user2")
            whenever(
                    getUserInSessionUseCase.handle(GetUsersInSessionUseCase.Query(sessionId.value))
                )
                .thenReturn(GetUsersInSessionUseCase.Response(users).asSuccess())

            val result = getUserInSessionApi.handle(sessionId.value)
            result shouldBe GetUsersInSessionApi.Response(users)
        }

        test("should return NotFound when session does not exist") {
            val sessionId = "nonExistingSessionId"
            whenever(getUserInSessionUseCase.handle(GetUsersInSessionUseCase.Query(sessionId)))
                .thenReturn(GetUsersInSessionUseCase.Errors.SessionNotFound(sessionId).asFailure())

            assertThrows<GetUsersInSessionUseCase.Errors.SessionNotFound> {
                getUserInSessionApi.handle(sessionId)
            }
        }
    })
