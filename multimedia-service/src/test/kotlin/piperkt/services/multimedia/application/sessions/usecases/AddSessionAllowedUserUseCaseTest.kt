package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.matchers.shouldBe
import mocks.MockedEventPublisher
import mocks.repositories.InMemorySessionRepository

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
                    addSessionAllowedUserUseCase.handle(
                        AddSessionAllowedUserUseCase.Command(
                            session.id.value,
                            userToAdd.username.value
                        )
                    )
                result.isSuccess shouldBe true
                eventPublisher.publishedEvents shouldBe
                    listOf(
                        AddSessionAllowedUserUseCase.Events.AllowedUserAdded(session.id, userToAdd)
                    )
            }

            test("should return SessionNotFound error if session does not exist") {
                val fakeSessionId = "fakeSessionId"
                val result =
                    addSessionAllowedUserUseCase.handle(
                        AddSessionAllowedUserUseCase.Command(
                            fakeSessionId,
                            UsersData.john().username.value
                        )
                    )
                result.isFailure shouldBe true
                result.exceptionOrNull() shouldBe
                    AddSessionAllowedUserUseCase.Errors.SessionNotFound(fakeSessionId)
            }

            test("should return UserAlreadyAllowed error if user is already allowed") {
                val john = UsersData.john()
                val session = sessionRepository.createSession(listOf(john.username.value))
                val result =
                    addSessionAllowedUserUseCase.handle(
                        AddSessionAllowedUserUseCase.Command(session.id.value, john.username.value)
                    )
                result.isFailure shouldBe true
                result.exceptionOrNull() shouldBe
                    AddSessionAllowedUserUseCase.Errors.UserAlreadyAllowed(session.id, john)
            }
        }
    })
