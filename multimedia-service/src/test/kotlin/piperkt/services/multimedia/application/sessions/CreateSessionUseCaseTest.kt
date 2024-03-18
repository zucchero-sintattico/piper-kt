package piperkt.services.multimedia.application.sessions

import base.Test
import data.SessionsData
import io.kotest.matchers.shouldBe
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import piperkt.services.multimedia.application.EventPublisher
import piperkt.services.multimedia.domain.sessions.SessionRepository
import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.UserId

class CreateSessionUseCaseTest :
    Test.Unit.FunSpec({
        val sessionRepository = mock<SessionRepository>()
        val eventPublisher = mock<EventPublisher>()
        val createSessionUseCase = CreateSessionUseCase(sessionRepository, eventPublisher)

        val session = SessionsData.fromUsers(setOf(User(UserId("user1")), User(UserId("user2"))))
        val users = session.allowedUsers.map { it.username.value }

        test("should create session and publish SessionCreated event") {
            whenever(sessionRepository.createSession(users)).thenReturn(session)
            val result = createSessionUseCase.handle(CreateSessionUseCase.Command(users))
            result.isSuccess shouldBe true
            verify(sessionRepository).createSession(users)
            verify(eventPublisher).publish(CreateSessionUseCase.Events.SessionCreated(session))
        }
    })
