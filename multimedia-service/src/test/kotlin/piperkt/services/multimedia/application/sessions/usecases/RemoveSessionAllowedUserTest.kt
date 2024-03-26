package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mocks.MockedSessionEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.application.usecases.RemoveSessionAllowedUser
import piperkt.services.multimedia.application.usecases.RemoveSessionAllowedUser.Command
import piperkt.services.multimedia.application.usecases.RemoveSessionAllowedUser.Errors.SessionNotFound
import piperkt.services.multimedia.application.usecases.RemoveSessionAllowedUser.Errors.UserNotInSession
import piperkt.services.multimedia.domain.events.SessionEvent.AllowedUserRemoved

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
                val session = sessionRepository.createSession(users.map { it.username.value })
                val result =
                    removeSessionAllowedUser(Command(session.id.value, userToRemove.username.value))
                result shouldBe success()
                eventPublisher.publishedEvents shouldBe
                    listOf(AllowedUserRemoved(session.id, userToRemove))
            }

            test("should return SessionNotFound error if session does not exist") {
                val fakeSessionId = "fakeSessionId"
                val fakeUser = "fakeUser"
                val result = removeSessionAllowedUser(Command(fakeSessionId, fakeUser))
                result shouldBe SessionNotFound(fakeSessionId).asFailure()
            }

            test("should return UserNotInSession error if user is not in the session") {
                val users = listOf(UsersData.john())
                val userToRemove = UsersData.jane()
                val session = sessionRepository.createSession(users.map { it.username.value })
                val result =
                    removeSessionAllowedUser(Command(session.id.value, userToRemove.username.value))
                result shouldBe
                    UserNotInSession(session.id.value, userToRemove.username.value).asFailure()
            }
        }
    })
