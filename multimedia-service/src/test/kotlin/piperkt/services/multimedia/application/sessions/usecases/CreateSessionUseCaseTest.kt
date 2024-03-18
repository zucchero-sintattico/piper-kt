package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.SessionsData
import io.kotest.matchers.shouldBe
import mocks.MockedEventPublisher
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import piperkt.services.multimedia.domain.sessions.SessionRepository
import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.Username

class CreateSessionUseCaseTest :
    Test.Unit.FunSpec({
        val sessionRepository = mock<SessionRepository>()
        val eventPublisher = MockedEventPublisher()
        val createSessionUseCase = CreateSessionUseCase(sessionRepository, eventPublisher)

        val session =
            SessionsData.fromUsers(setOf(User(Username("user1")), User(Username("user2"))))
        val users = session.allowedUsers.map { it.username.value }

        beforeEach { eventPublisher.clear() }

        test("should create session and publish SessionCreated event") {
            whenever(sessionRepository.createSession(users)).thenReturn(session)
            val result = createSessionUseCase.handle(CreateSessionUseCase.Command(users))
            result.isSuccess shouldBe true
            verify(sessionRepository).createSession(users)
            eventPublisher.publishedEvents shouldBe
                listOf(CreateSessionUseCase.Events.SessionCreated(session))
        }
    })
