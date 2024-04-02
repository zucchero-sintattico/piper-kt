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
import piperkt.services.multimedia.application.usecases.LeaveSession
import piperkt.services.multimedia.application.usecases.LeaveSession.Command
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionFactory
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
                val users = setOf(john().id, jane().id)
                val session = SessionFactory.withParticipants(users, users)
                sessionRepository.save(session)
                val result = leaveSession(Command(session.id, jane().id))
                result shouldBe success()
            }

            test("should return SessionNotFound error if session does not exist") {
                val fakeSessionId = SessionId("fakeSessionId")
                val result = leaveSession(Command(fakeSessionId, john().id))
                result shouldBe SessionErrors.SessionNotFound(fakeSessionId).asFailure()
            }

            test("should return UserNotParticipant error if user is not in the session") {
                val session = SessionFactory.empty()
                sessionRepository.save(session)
                val result = leaveSession(Command(session.id, jane().id))
                result shouldBe SessionErrors.UserNotParticipant(session.id, jane().id).asFailure()
            }
        }
    })
