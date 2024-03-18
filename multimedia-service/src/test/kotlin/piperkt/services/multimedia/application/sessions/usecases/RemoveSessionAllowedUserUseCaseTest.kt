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

class RemoveSessionAllowedUserUseCaseTest :
    Test.Unit.FunSpec({
        context("a session") {
            val session = SessionsData.simpleSession()
            val sessionRepository = mock<SessionRepository>()
            val eventPublisher = MockedEventPublisher()
            val removeSessionAllowedUserUseCase =
                RemoveSessionAllowedUserUseCase(sessionRepository, eventPublisher)

            beforeEach { eventPublisher.clear() }

            test("should allow to remove an allowed user from the session") {
                val user = User.fromUsername("user")
                whenever(sessionRepository.findById(session.id)).thenReturn(session)
                whenever(sessionRepository.removeAllowedUser(session.id, user)).thenReturn(true)
                val result =
                    removeSessionAllowedUserUseCase.handle(
                        RemoveSessionAllowedUserUseCase.Command(
                            session.id.value,
                            user.username.value
                        )
                    )
                result.isSuccess shouldBe true
                verify(sessionRepository).removeAllowedUser(session.id, user)
                eventPublisher.publishedEvents shouldBe
                    listOf(
                        RemoveSessionAllowedUserUseCase.Events.AllowedUserRemoved(session.id, user)
                    )
            }

            test("should return SessionNotFound error if session does not exist") {
                val user = User.fromUsername("user")
                whenever(sessionRepository.findById(session.id)).thenReturn(null)
                val result =
                    removeSessionAllowedUserUseCase.handle(
                        RemoveSessionAllowedUserUseCase.Command(
                            session.id.value,
                            user.username.value
                        )
                    )
                result.isFailure shouldBe true
                result.exceptionOrNull() shouldBe
                    RemoveSessionAllowedUserUseCase.Errors.SessionNotFound(session.id.value)
            }

            test("should return UserNotInSession error if user is not in the session") {
                val user = User.fromUsername("user")
                whenever(sessionRepository.findById(session.id)).thenReturn(session)
                whenever(sessionRepository.removeAllowedUser(session.id, user)).thenReturn(false)
                val result =
                    removeSessionAllowedUserUseCase.handle(
                        RemoveSessionAllowedUserUseCase.Command(
                            session.id.value,
                            user.username.value
                        )
                    )
                result.isFailure shouldBe true
                result.exceptionOrNull() shouldBe
                    RemoveSessionAllowedUserUseCase.Errors.UserNotInSession(
                        session.id.value,
                        user.username.value
                    )
            }
        }
    })
