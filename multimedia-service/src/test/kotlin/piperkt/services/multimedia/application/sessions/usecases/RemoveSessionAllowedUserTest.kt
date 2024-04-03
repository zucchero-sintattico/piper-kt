package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData.jane
import data.UsersData.john
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mocks.publishers.MockedSessionEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.application.usecases.internal.RemoveSessionAllowedUser
import piperkt.services.multimedia.application.usecases.internal.RemoveSessionAllowedUser.Command
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionFactory
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
                val users = setOf(john().id, jane().id)
                val session = SessionFactory.fromAllowedUsers(users)
                sessionRepository.save(session)
                val result = removeSessionAllowedUser(Command(session.id, jane().id))
                result shouldBe success()
            }

            test("should return SessionNotFound error if session does not exist") {
                val fakeSessionId = SessionId("fakeSessionId")
                val result = removeSessionAllowedUser(Command(fakeSessionId, john().id))
                result shouldBe SessionErrors.SessionNotFound(fakeSessionId).asFailure()
            }

            test("should return UserNotAllowed error if user is not in the session") {
                val session = SessionFactory.empty()
                sessionRepository.save(session)
                val result = removeSessionAllowedUser(Command(session.id, jane().id))
                result shouldBe SessionErrors.UserNotAllowed(session.id, jane().id).asFailure()
            }
        }
    })
