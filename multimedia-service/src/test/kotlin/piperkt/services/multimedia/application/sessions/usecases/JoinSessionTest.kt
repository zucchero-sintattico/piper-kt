package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mocks.MockedSessionEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.application.usecases.JoinSession
import piperkt.services.multimedia.application.usecases.JoinSession.Command
import piperkt.services.multimedia.domain.session.Session
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionId

class JoinSessionTest :
    Test.Unit,
    FunSpec({
        context("a session") {
            val sessionRepository = InMemorySessionRepository()
            val eventPublisher = MockedSessionEventPublisher()
            val joinSession = JoinSession(sessionRepository, eventPublisher)

            beforeEach {
                eventPublisher.clear()
                sessionRepository.clear()
            }

            test(
                "should allow to add a participant to the session if user is allowed to join the session"
            ) {
                val allowedUsers = listOf(UsersData.john())
                val sessionId = SessionId("sessionId")
                val session = Session(id = sessionId, allowedUsers = allowedUsers.map { it.id })
                sessionRepository.save(session)
                val result = joinSession(Command(sessionId, allowedUsers[0].id))
                result shouldBe success()
            }

            test("should return SessionNotFound error if session does not exist") {
                val fakeSessionId = SessionId("fakeSessionId")
                val result = joinSession(Command(fakeSessionId, UsersData.john().id))
                result shouldBe SessionErrors.SessionNotFound(fakeSessionId).asFailure()
            }

            test("should return UserNotAllowed error if user is not allowed to join the session") {
                val allowedUsers = listOf(UsersData.john()).map { it.id }
                val session = Session(id = SessionId("sessionId"), allowedUsers = allowedUsers)
                sessionRepository.save(session)
                val result = joinSession(Command(session.id, UsersData.jane().id))
                result shouldBe
                    SessionErrors.UserNotAllowed(session.id, UsersData.jane().id).asFailure()
            }

            test("should return UserAlreadyParticipant error if user is already a participant") {
                val allowedUsers = listOf(UsersData.john()).map { it.id }
                val session =
                    Session(
                        id = SessionId("sessionId"),
                        allowedUsers = allowedUsers,
                        participants = allowedUsers
                    )
                sessionRepository.save(session)
                val result = joinSession(Command(session.id, allowedUsers[0]))
                result shouldBe
                    SessionErrors.UserAlreadyParticipant(session.id, allowedUsers[0]).asFailure()
            }
        }
    })
