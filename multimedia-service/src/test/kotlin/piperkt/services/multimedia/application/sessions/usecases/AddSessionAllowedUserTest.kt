package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mocks.MockedSessionEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.application.usecases.internal.AddSessionAllowedUser
import piperkt.services.multimedia.application.usecases.internal.AddSessionAllowedUser.Command
import piperkt.services.multimedia.domain.events.SessionEvent.AllowedUserAdded
import piperkt.services.multimedia.domain.session.Session
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionId

class AddSessionAllowedUserTest :
    Test.Unit,
    FunSpec({
        context("a session") {
            val sessionRepository = InMemorySessionRepository()
            val eventPublisher = MockedSessionEventPublisher()
            val addSessionAllowedUser = AddSessionAllowedUser(sessionRepository, eventPublisher)

            beforeEach {
                eventPublisher.clear()
                sessionRepository.clear()
            }

            test("should allow to add an allowed user to the session") {
                val users = listOf(UsersData.john())
                val userToAdd = UsersData.jane()
                val session =
                    Session(id = SessionId("sessionId"), allowedUsers = users.map { it.id })
                sessionRepository.save(session)
                val result = addSessionAllowedUser(Command(session.id, userToAdd.id))
                result shouldBe success()
                eventPublisher.publishedEvents shouldBe
                    listOf(AllowedUserAdded(session.id, userToAdd.id))
            }

            test("should return SessionNotFound error if session does not exist") {
                val fakeSessionId = "fakeSessionId"
                val user = UsersData.john()
                val result = addSessionAllowedUser(Command(SessionId(fakeSessionId), user.id))
                result shouldBe SessionErrors.SessionNotFound(SessionId(fakeSessionId)).asFailure()
            }

            test("should return UserAlreadyAllowed error if user is already allowed") {
                val user = UsersData.john()
                val session = Session(id = SessionId("sessionId"), allowedUsers = listOf(user.id))
                sessionRepository.save(session)
                val result = addSessionAllowedUser(Command(session.id, user.id))
                result shouldBe SessionErrors.UserAlreadyAllowed(session.id, user.id).asFailure()
            }
        }
    })
