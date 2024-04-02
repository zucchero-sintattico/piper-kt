package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData.jane
import data.UsersData.john
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mocks.MockedSessionEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.application.usecases.internal.AddSessionAllowedUser
import piperkt.services.multimedia.application.usecases.internal.AddSessionAllowedUser.Command
import piperkt.services.multimedia.domain.events.SessionEvent.AllowedUserAdded
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionFactory
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
                val allowedUsers = setOf(john().id)
                val session = SessionFactory.fromAllowedUsersIds(allowedUsers)
                sessionRepository.save(session)
                val userToAdd = jane()
                val result = addSessionAllowedUser(Command(session.id, userToAdd.id))
                result shouldBe success()
                eventPublisher.publishedEvents shouldBe
                    listOf(AllowedUserAdded(session.id, userToAdd.id))
            }

            test("should return SessionNotFound error if session does not exist") {
                val fakeSessionId = "fakeSessionId"
                val user = john()
                val result = addSessionAllowedUser(Command(SessionId(fakeSessionId), user.id))
                result shouldBe SessionErrors.SessionNotFound(SessionId(fakeSessionId)).asFailure()
            }

            test("should return UserAlreadyAllowed error if user is already allowed") {
                val users = setOf(john().id)
                val session = SessionFactory.fromAllowedUsersIds(users)
                sessionRepository.save(session)
                val result = addSessionAllowedUser(Command(session.id, users.first()))
                result shouldBe
                    SessionErrors.UserAlreadyAllowed(session.id, users.first()).asFailure()
            }
        }
    })
