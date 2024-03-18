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

class AddSessionAllowedUserUseCaseTest :
    Test.Unit.FunSpec({
        context("a session") {
            val session = SessionsData.simpleSession()
            val sessionRepository = mock<SessionRepository>()
            val eventPublisher = MockedEventPublisher()
            val addSessionAllowedUserUseCase =
                AddSessionAllowedUserUseCase(sessionRepository, eventPublisher)

            beforeEach { eventPublisher.clear() }

            test("should allow to add an allowed user to the session") {
                val user = User.fromUsername("user")
                whenever(sessionRepository.findById(session.id)).thenReturn(session)
                whenever(sessionRepository.addAllowedUser(session.id, user)).thenReturn(true)
                val result =
                    addSessionAllowedUserUseCase.handle(
                        AddSessionAllowedUserUseCase.Command(session.id.value, user.username.value)
                    )
                result.isSuccess shouldBe true
                verify(sessionRepository).addAllowedUser(session.id, user)
                eventPublisher.publishedEvents shouldBe
                    listOf(AddSessionAllowedUserUseCase.Events.AllowedUserAdded(session.id, user))
            }

            test("should return SessionNotFound error if session does not exist") {
                val user = User.fromUsername("user")
                whenever(sessionRepository.findById(session.id)).thenReturn(null)
                val result =
                    addSessionAllowedUserUseCase.handle(
                        AddSessionAllowedUserUseCase.Command(session.id.value, user.username.value)
                    )
                result.isFailure shouldBe true
                result.exceptionOrNull() shouldBe
                    AddSessionAllowedUserUseCase.Errors.SessionNotFound(session.id.value)
            }

            test("should return UserAlreadyAllowed error if user is already allowed") {
                whenever(sessionRepository.findById(session.id)).thenReturn(session)
                val result =
                    addSessionAllowedUserUseCase.handle(
                        AddSessionAllowedUserUseCase.Command(
                            session.id.value,
                            session.allowedUsers.first().username.value
                        )
                    )
                result.isFailure shouldBe true
                result.exceptionOrNull() shouldBe
                    AddSessionAllowedUserUseCase.Errors.UserAlreadyAllowed(
                        session.id,
                        session.allowedUsers.first()
                    )
            }
        }
    })
