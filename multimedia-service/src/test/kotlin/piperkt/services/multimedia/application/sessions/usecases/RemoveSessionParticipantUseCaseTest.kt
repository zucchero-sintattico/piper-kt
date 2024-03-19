package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.matchers.shouldBe
import mocks.MockedEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.failure
import piperkt.services.multimedia.application.success

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
                    removeSessionParticipantUseCase.handle(
                        RemoveSessionParticipantUseCase.Command(
                            session.id.value,
                            users[0].username.value
                        )
                    )
                result shouldBe success()
                eventPublisher.publishedEvents shouldBe
                    listOf(
                        RemoveSessionParticipantUseCase.Events.ParticipantRemoved(
                            session.id,
                            users[0]
                        )
                    )
            }

            test("should return SessionNotFound error if session does not exist") {
                val fakeSessionId = "fakeSessionId"
                val fakeUser = "fakeUser"
                val result =
                    removeSessionParticipantUseCase.handle(
                        RemoveSessionParticipantUseCase.Command(fakeSessionId, fakeUser)
                    )
                result shouldBe
                    failure(RemoveSessionParticipantUseCase.Errors.SessionNotFound(fakeSessionId))
            }

            test("should return UserNotInSession error if user is not in the session") {
                val session = sessionRepository.createSession(emptyList())
                val userNotInSession = UsersData.john()
                val result =
                    removeSessionParticipantUseCase.handle(
                        RemoveSessionParticipantUseCase.Command(
                            session.id.value,
                            userNotInSession.username.value
                        )
                    )
                result shouldBe
                    failure(
                        RemoveSessionParticipantUseCase.Errors.UserNotInSession(
                            session.id.value,
                            userNotInSession.username.value
                        )
                    )
            }
        }
    })
