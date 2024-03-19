package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.matchers.shouldBe
import mocks.MockedEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.sessions.usecases.AddSessionParticipantUseCase.Command
import piperkt.services.multimedia.application.sessions.usecases.AddSessionParticipantUseCase.Errors.*
import piperkt.services.multimedia.application.sessions.usecases.AddSessionParticipantUseCase.Events.ParticipantAdded
import piperkt.services.multimedia.application.success

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
                    addSessionParticipantUseCase(
                        Command(session.id.value, allowedUsers[0].username.value)
                    )
                result shouldBe success()
                eventPublisher.publishedEvents shouldBe
                    listOf(ParticipantAdded(session.id, allowedUsers[0]))
            }

            test("should return SessionNotFound error if session does not exist") {
                val fakeSessionId = "fakeSessionId"
                val result =
                    addSessionParticipantUseCase(
                        Command(fakeSessionId, UsersData.john().username.value)
                    )
                result shouldBe SessionNotFound(fakeSessionId).asFailure()
            }

            test("should return UserNotAllowed error if user is not allowed to join the session") {
                val allowedUsers = listOf(UsersData.john())
                val session =
                    sessionRepository.createSession(allowedUsers.map { it.username.value })
                val result =
                    addSessionParticipantUseCase(
                        Command(session.id.value, UsersData.jane().username.value)
                    )
                result shouldBe
                    UserNotAllowed(session.id.value, UsersData.jane().username.value).asFailure()
            }

            test("should return UserAlreadyParticipant error if user is already a participant") {
                val allowedUsers = listOf(UsersData.john())
                val session =
                    sessionRepository.createSession(
                        allowedUsers = allowedUsers.map { it.username.value }
                    )
                addSessionParticipantUseCase(
                    Command(session.id.value, allowedUsers[0].username.value)
                )
                val result =
                    addSessionParticipantUseCase(
                        Command(session.id.value, allowedUsers[0].username.value)
                    )
                result shouldBe
                    UserAlreadyParticipant(session.id.value, allowedUsers[0].username.value)
                        .asFailure()
            }
        }
    })
