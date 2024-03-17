package piperkt.services.multimedia.application.sessions

import base.MicronautTest
import io.kotest.matchers.shouldBe
import mocks.repositories.SessionRepositoryMocks
import piperkt.services.multimedia.domain.sessions.Session
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.sessions.SessionRepository
import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.UserId

class GetUsersInSessionUseCaseTest :
    MicronautTest({
        val sessionId = SessionId("sessionId")
        val users = setOf(User.fromUserId(UserId("user1")), User.fromUserId(UserId("user2")))
        val sessionRepository: SessionRepository =
            SessionRepositoryMocks.fromSessions(
                Session.create(sessionId, users.toList(), users.toList())
            )
        val getUsersInSessionUseCase = GetUsersInSessionUseCase(sessionRepository)

        test("should return users when session exists") {
            val result =
                getUsersInSessionUseCase.handle(GetUsersInSessionUseCase.Query(sessionId.value))

            result.isSuccess shouldBe true
            result.getOrNull()?.users shouldBe users.map { it.username.value }.toSet()
        }

        test("should return SessionNotFound error when session does not exist") {
            val fakeSessionId = SessionId("fakeSessionId")
            val result =
                getUsersInSessionUseCase.handle(GetUsersInSessionUseCase.Query(fakeSessionId.value))

            result.isFailure shouldBe true
            result.exceptionOrNull() shouldBe
                GetUsersInSessionUseCase.Errors.SessionNotFound(fakeSessionId.value)
        }
    })
