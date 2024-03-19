package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.matchers.shouldBe
import mocks.MockedEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.failure
import piperkt.services.multimedia.application.success

class RemoveSessionAllowedUserUseCaseTest :
    Test.Unit.FunSpec({
        context("a session") {
            val sessionRepository = InMemorySessionRepository()
            val eventPublisher = MockedEventPublisher()
            val removeSessionAllowedUserUseCase =
                RemoveSessionAllowedUserUseCase(sessionRepository, eventPublisher)

            beforeEach {
                sessionRepository.clear()
                eventPublisher.clear()
            }

            test("should allow to remove an allowed user from the session") {
                val users = listOf(UsersData.john(), UsersData.jane())
                val userToRemove = users[0]
                val session = sessionRepository.createSession(users.map { it.username.value })
                val result =
                    removeSessionAllowedUserUseCase.handle(
                        RemoveSessionAllowedUserUseCase.Command(
                            session.id.value,
                            userToRemove.username.value
                        )
                    )
                result shouldBe success()
                eventPublisher.publishedEvents shouldBe
                    listOf(
                        RemoveSessionAllowedUserUseCase.Events.AllowedUserRemoved(
                            session.id,
                            userToRemove
                        )
                    )
            }

            test("should return SessionNotFound error if session does not exist") {
                val fakeSessionId = "fakeSessionId"
                val fakeUser = "fakeUser"
                val result =
                    removeSessionAllowedUserUseCase.handle(
                        RemoveSessionAllowedUserUseCase.Command(fakeSessionId, fakeUser)
                    )
                result shouldBe
                    failure(RemoveSessionAllowedUserUseCase.Errors.SessionNotFound(fakeSessionId))
            }

            test("should return UserNotInSession error if user is not in the session") {
                val users = listOf(UsersData.john())
                val userToRemove = UsersData.jane()
                val session = sessionRepository.createSession(users.map { it.username.value })
                val result =
                    removeSessionAllowedUserUseCase.handle(
                        RemoveSessionAllowedUserUseCase.Command(
                            session.id.value,
                            userToRemove.username.value
                        )
                    )
                result shouldBe
                    failure(
                        RemoveSessionAllowedUserUseCase.Errors.UserNotInSession(
                            session.id.value,
                            userToRemove.username.value
                        )
                    )
            }
        }
    })
