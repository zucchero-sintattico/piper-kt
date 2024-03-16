package piperkt.services.multimedia.application.sessions

import base.MicronautTest
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import piperkt.services.multimedia.domain.sessions.Session
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.sessions.SessionRepository
import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.UserId

class GetUsersInSessionUseCaseTest :
    MicronautTest({
        val sessionRepository: SessionRepository = mock()
        val getUsersInSessionUseCase = GetUsersInSessionUseCase(sessionRepository)

        test("should return users when session exists") {
            val sessionId = SessionId("sessionId")
            val users = setOf(User.fromUserId(UserId("user1")), User.fromUserId(UserId("user2")))
            val session = Session.create(sessionId, users.toList(), users.toList())
            whenever(sessionRepository.findById(sessionId)).thenReturn(session)

            val result =
                getUsersInSessionUseCase.handle(GetUsersInSessionUseCase.Query(sessionId.value))

            result.isSuccess shouldBe true
            result.getOrNull()?.users shouldBe users.map { it.username.value }.toSet()
        }

        test("should return SessionNotFound error when session does not exist") {
            val sessionId = "nonExistingSessionId"
            whenever(sessionRepository.findById(SessionId(sessionId))).thenReturn(null)

            val result = getUsersInSessionUseCase.handle(GetUsersInSessionUseCase.Query(sessionId))

            result.isFailure shouldBe true
            result.exceptionOrNull() shouldBe
                GetUsersInSessionUseCase.Errors.SessionNotFound(sessionId)
        }
    })
