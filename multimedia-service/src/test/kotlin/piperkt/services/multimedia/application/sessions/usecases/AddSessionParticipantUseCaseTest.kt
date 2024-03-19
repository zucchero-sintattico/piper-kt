package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.matchers.shouldBe
import mocks.MockedEventPublisher
import mocks.repositories.InMemorySessionRepository

class AddSessionParticipantUseCaseTest :
    Test.Unit.FunSpec({
        context("a session") {
            val sessionRepository = InMemorySessionRepository()
            val eventPublisher = MockedEventPublisher()
            val addSessionParticipantUseCase =
                AddSessionParticipantUseCase(sessionRepository, eventPublisher)

            beforeEach {
                eventPublisher.clear()
                sessionRepository.clear()
            }

            test(
                "should allow to add a participant to the session if user is allowed to join the session"
            ) {
                val allowedUsers = listOf(UsersData.john())
                val session =
                    sessionRepository.createSession(allowedUsers.map { it.username.value })
                val result =
                    addSessionParticipantUseCase.handle(
                        AddSessionParticipantUseCase.Command(
                            session.id.value,
                            allowedUsers[0].username.value
                        )
                    )
                result.isSuccess shouldBe true
                eventPublisher.publishedEvents shouldBe
                    listOf(
                        AddSessionParticipantUseCase.Events.ParticipantAdded(
                            session.id,
                            allowedUsers[0]
                        )
                    )
            }

            test("should return SessionNotFound error if session does not exist") {
                val fakeSessionId = "fakeSessionId"
                val result =
                    addSessionParticipantUseCase.handle(
                        AddSessionParticipantUseCase.Command(
                            fakeSessionId,
                            UsersData.john().username.value
                        )
                    )
                result.isFailure shouldBe true
                result.exceptionOrNull() shouldBe
                    AddSessionParticipantUseCase.Errors.SessionNotFound(fakeSessionId)
            }

            test("should return UserNotAllowed error if user is not allowed to join the session") {
                val allowedUsers = listOf(UsersData.john())
                val session =
                    sessionRepository.createSession(allowedUsers.map { it.username.value })
                val result =
                    addSessionParticipantUseCase.handle(
                        AddSessionParticipantUseCase.Command(
                            session.id.value,
                            UsersData.jane().username.value
                        )
                    )
                result.isFailure shouldBe true
                result.exceptionOrNull() shouldBe
                    AddSessionParticipantUseCase.Errors.UserNotAllowed(
                        session.id.value,
                        UsersData.jane().username.value
                    )
            }

            test("should return UserAlreadyParticipant error if user is already a participant") {
                val allowedUsers = listOf(UsersData.john())
                val session =
                    sessionRepository.createSession(
                        allowedUsers = allowedUsers.map { it.username.value }
                    )
                addSessionParticipantUseCase.handle(
                    AddSessionParticipantUseCase.Command(
                        session.id.value,
                        allowedUsers[0].username.value
                    )
                )
                val result =
                    addSessionParticipantUseCase.handle(
                        AddSessionParticipantUseCase.Command(
                            session.id.value,
                            allowedUsers[0].username.value
                        )
                    )
                result.isFailure shouldBe true
                result.exceptionOrNull() shouldBe
                    AddSessionParticipantUseCase.Errors.UserAlreadyParticipant(
                        session.id.value,
                        allowedUsers[0].username.value
                    )
            }
        }
    })
