package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.matchers.shouldBe
import mocks.MockedEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.application.usecases.AddSessionAllowedUserUseCase
import piperkt.services.multimedia.application.usecases.AddSessionAllowedUserUseCase.Command
import piperkt.services.multimedia.application.usecases.AddSessionAllowedUserUseCase.Errors.SessionNotFound
import piperkt.services.multimedia.application.usecases.AddSessionAllowedUserUseCase.Errors.UserAlreadyAllowed
import piperkt.services.multimedia.application.usecases.AddSessionAllowedUserUseCase.Events.AllowedUserAdded

class AddSessionAllowedUserUseCaseTest :
    Test.Unit.FunSpec({
        context("a session") {
            val sessionRepository = InMemorySessionRepository()
            val eventPublisher = MockedEventPublisher()
            val addSessionAllowedUserUseCase =
                AddSessionAllowedUserUseCase(sessionRepository, eventPublisher)

            beforeEach {
                eventPublisher.clear()
                sessionRepository.clear()
            }

            test("should allow to add an allowed user to the session") {
                val users = listOf(UsersData.john())
                val userToAdd = UsersData.jane()
                val session = sessionRepository.createSession(users.map { it.username.value })
                val result =
                    addSessionAllowedUserUseCase(
                        Command(session.id.value, userToAdd.username.value)
                    )
                result shouldBe success()
                eventPublisher.publishedEvents shouldBe
                    listOf(AllowedUserAdded(session.id, userToAdd))
            }

            test("should return SessionNotFound error if session does not exist") {
                val fakeSessionId = "fakeSessionId"
                val result =
                    addSessionAllowedUserUseCase(
                        Command(fakeSessionId, UsersData.john().username.value)
                    )
                result shouldBe SessionNotFound(fakeSessionId).asFailure()
            }

            test("should return UserAlreadyAllowed error if user is already allowed") {
                val john = UsersData.john()
                val session = sessionRepository.createSession(listOf(john.username.value))
                val result =
                    addSessionAllowedUserUseCase(Command(session.id.value, john.username.value))
                result shouldBe UserAlreadyAllowed(session.id, john).asFailure()
            }
        }
    })
