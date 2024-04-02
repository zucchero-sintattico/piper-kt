package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mocks.MockedSessionEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.application.usecases.internal.RemoveSessionAllowedUser
import piperkt.services.multimedia.application.usecases.internal.RemoveSessionAllowedUser.Command
import piperkt.services.multimedia.domain.session.Session
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionId

class RemoveSessionAllowedUserTest :
    Test.Unit,
    FunSpec({
        context("a session") {
            val sessionRepository = InMemorySessionRepository()
            val eventPublisher = MockedSessionEventPublisher()
            val removeSessionAllowedUser =
                RemoveSessionAllowedUser(sessionRepository, eventPublisher)

            beforeEach {
                sessionRepository.clear()
                eventPublisher.clear()
            }

            test("should allow to remove an allowed user from the session") {
                val users = listOf(UsersData.john(), UsersData.jane())
                val userToRemove = users[0]
                val sessionId = SessionId("sessionId")
                val session = Session(id = sessionId, allowedUsers = users.map { it.id })
                sessionRepository.save(session)
                val result = removeSessionAllowedUser(Command(sessionId, userToRemove.id))
                result shouldBe success()
            }

            test("should return SessionNotFound error if session does not exist") {
                val fakeSessionId = SessionId("fakeSessionId")
                val user = UsersData.john()
                val result = removeSessionAllowedUser(Command(fakeSessionId, user.id))
                result shouldBe SessionErrors.SessionNotFound(fakeSessionId).asFailure()
            }

            test("should return UserNotAllowed error if user is not in the session") {
                val users = listOf(UsersData.john())
                val userToRemove = UsersData.jane()
                val sessionId = SessionId("sessionId")
                val session = Session(id = sessionId, allowedUsers = users.map { it.id })
                sessionRepository.save(session)
                val result = removeSessionAllowedUser(Command(sessionId, userToRemove.id))
                result shouldBe SessionErrors.UserNotAllowed(sessionId, userToRemove.id).asFailure()
            }
        }
    })
