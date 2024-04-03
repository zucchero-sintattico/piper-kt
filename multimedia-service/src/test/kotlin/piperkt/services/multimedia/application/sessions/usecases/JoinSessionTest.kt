package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData.john
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mocks.publishers.MockedSessionEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.success
import piperkt.services.multimedia.application.usecases.JoinSession
import piperkt.services.multimedia.application.usecases.JoinSession.Command
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionFactory
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
                val allowedUsers = setOf(john().id)
                val session = SessionFactory.fromAllowedUsers(allowedUsers)
                sessionRepository.save(session)
                val result = joinSession(Command(session.id, john().id))
                result shouldBe success()
            }

            test("should return SessionNotFound error if session does not exist") {
                val fakeSessionId = SessionId("fakeSessionId")
                val result = joinSession(Command(fakeSessionId, john().id))
                result shouldBe SessionErrors.SessionNotFound(fakeSessionId).asFailure()
            }

            test("should return UserNotAllowed error if user is not allowed to join the session") {
                val session = SessionFactory.empty()
                sessionRepository.save(session)
                val result = joinSession(Command(session.id, john().id))
                result shouldBe SessionErrors.UserNotAllowed(session.id, john().id).asFailure()
            }

            test("should return UserAlreadyParticipant error if user is already a participant") {
                val users = setOf(john().id)
                val session = SessionFactory.fromAllowedParticipants(users)
                sessionRepository.save(session)
                val result = joinSession(Command(session.id, users.first()))
                result shouldBe
                    SessionErrors.UserAlreadyParticipant(session.id, users.first()).asFailure()
            }
        }
    })
