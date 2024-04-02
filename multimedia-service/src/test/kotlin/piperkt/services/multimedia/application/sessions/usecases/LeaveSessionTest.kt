package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mocks.MockedSessionEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.application.usecases.LeaveSession
import piperkt.services.multimedia.application.usecases.LeaveSession.Command
import piperkt.services.multimedia.domain.session.Session
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionId

class LeaveSessionTest :
    Test.Unit,
    FunSpec({
        context("a session") {
            val sessionRepository = InMemorySessionRepository()
            val eventPublisher = MockedSessionEventPublisher()
            val leaveSession = LeaveSession(sessionRepository, eventPublisher)

            beforeEach {
                sessionRepository.clear()
                eventPublisher.clear()
            }

            test("should allow to remove a participant from the session") {
                val users = listOf(UsersData.john(), UsersData.jane())
                val sessionId = SessionId("sessionId")
                val session =
                    Session(
                        id = sessionId,
                        allowedUsers = users.map { it.id },
                    )
                session.addParticipant(users[0].id)
                sessionRepository.save(session)
                val result = leaveSession(Command(sessionId, users[0].id))
                result shouldBe success()
            }

            test("should return SessionNotFound error if session does not exist") {
                val fakeSessionId = SessionId("fakeSessionId")
                val result = leaveSession(Command(fakeSessionId, UsersData.john().id))
                result shouldBe SessionErrors.SessionNotFound(fakeSessionId).asFailure()
            }

            test("should return UserNotParticipant error if user is not in the session") {
                val users = listOf(UsersData.john(), UsersData.jane())
                val sessionId = SessionId("sessionId")
                val session =
                    Session(
                        id = sessionId,
                        allowedUsers = users.map { it.id },
                    )
                sessionRepository.save(session)
                val result = leaveSession(Command(sessionId, UsersData.jane().id))
                result shouldBe
                    SessionErrors.UserNotParticipant(sessionId, UsersData.jane().id).asFailure()
            }
        }
    })
