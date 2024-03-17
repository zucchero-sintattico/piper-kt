package piperkt.services.multimedia.interfaces.web.api

import base.MicronautTest
import io.kotest.matchers.shouldBe
import mocks.repositories.SessionRepositoryMocks
import org.junit.jupiter.api.assertThrows
import piperkt.services.multimedia.application.sessions.GetUsersInSessionUseCase
import piperkt.services.multimedia.domain.sessions.Session
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.UserId
import piperkt.services.multimedia.interfaces.web.controllers.GetUserInSessionController

class GetUserInSessionControllerTest :
    MicronautTest({
        val users = setOf(User(UserId("user1")), User(UserId("user2")))
        val session = Session.create(SessionId("sessionId"), users.toList(), users.toList())
        val sessionRepository = SessionRepositoryMocks.fromSessions(session)
        val getUserInSessionApi =
            GetUserInSessionController(GetUsersInSessionUseCase(sessionRepository))

        test("should return users when session exists") {
            val result = getUserInSessionApi.handle(session.id.value)
            result shouldBe GetUsersInSessionApi.Response(users.map { it.username.value }.toSet())
        }

        test("should return NotFound when session does not exist") {
            val fakeSessionId = SessionId("nonExistingSessionId")
            assertThrows<GetUsersInSessionUseCase.Errors.SessionNotFound> {
                getUserInSessionApi.handle(fakeSessionId.value)
            }
        }
    })
