package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.matchers.shouldBe
import mocks.MockedEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.application.usecases.RemoveSessionParticipantUseCase
import piperkt.services.multimedia.application.usecases.RemoveSessionParticipantUseCase.Command
import piperkt.services.multimedia.application.usecases.RemoveSessionParticipantUseCase.Errors.SessionNotFound
import piperkt.services.multimedia.application.usecases.RemoveSessionParticipantUseCase.Errors.UserNotInSession
import piperkt.services.multimedia.application.usecases.RemoveSessionParticipantUseCase.Events.ParticipantRemoved

class RemoveSessionParticipantUseCaseTest :
    Test.Unit.FunSpec({
        context("a session") {
            val sessionRepository = InMemorySessionRepository()
            val eventPublisher = MockedEventPublisher()
            val removeSessionParticipantUseCase =
                RemoveSessionParticipantUseCase(sessionRepository, eventPublisher)

            beforeEach {
                sessionRepository.clear()
                eventPublisher.clear()
            }

            test("should allow to remove a participant from the session") {
                val users = listOf(UsersData.john(), UsersData.jane())
                val session = sessionRepository.createSession(users.map { it.username.value })
                sessionRepository.addParticipant(session.id, users[0])
                val result =
                    removeSessionParticipantUseCase(
                        Command(session.id.value, users[0].username.value)
                    )
                result shouldBe success()
                eventPublisher.publishedEvents shouldBe
                    listOf(ParticipantRemoved(session.id, users[0]))
            }

            test("should return SessionNotFound error if session does not exist") {
                val fakeSessionId = "fakeSessionId"
                val fakeUser = "fakeUser"
                val result = removeSessionParticipantUseCase(Command(fakeSessionId, fakeUser))
                result shouldBe SessionNotFound(fakeSessionId).asFailure()
            }

            test("should return UserNotInSession error if user is not in the session") {
                val session = sessionRepository.createSession(emptyList())
                val userNotInSession = UsersData.john()
                val result =
                    removeSessionParticipantUseCase(
                        Command(session.id.value, userNotInSession.username.value)
                    )
                result shouldBe
                    UserNotInSession(session.id.value, userNotInSession.username.value).asFailure()
            }
        }
    })
