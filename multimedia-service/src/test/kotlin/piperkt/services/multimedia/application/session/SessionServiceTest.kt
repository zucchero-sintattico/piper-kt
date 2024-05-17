package piperkt.services.multimedia.application.session

import base.UnitTest
import data.UsersData.jane
import data.UsersData.john
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import mocks.publishers.MockedSessionEventPublisher
import mocks.repositories.InMemoryDirectRepository
import mocks.repositories.InMemoryServerRepository
import mocks.repositories.InMemorySessionRepository
import piperkt.events.SessionEvent
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionId

class SessionServiceTest :
    UnitTest.FunSpec({
        val sessionRepository = InMemorySessionRepository()
        val serverRepository = InMemoryServerRepository()
        val directRepository = InMemoryDirectRepository()
        val sessionEventPublisher = MockedSessionEventPublisher()
        val sessionService =
            SessionService(
                sessionRepository,
                serverRepository,
                directRepository,
                sessionEventPublisher
            )

        afterEach {
            sessionRepository.clear()
            serverRepository.clear()
            directRepository.clear()
            sessionEventPublisher.clear()
        }

        test("should retrieve a session") {
            val session = SessionFactory.create()
            sessionRepository.save(session)
            val retrievedSession = sessionService.getSession(session.id)
            retrievedSession shouldBe session
        }

        test("should throw an exception when trying to retrieve a non-existing session") {
            shouldThrow<SessionErrors.SessionNotFound> {
                sessionService.getSession(SessionId("non-existing-session"))
            }
        }

        test("should create a session") {
            val allowedUsers = setOf(john().id, jane().id)
            val command = SessionService.Command.CreateSession(allowedUsers)
            val session = sessionService.createSession(command)
            sessionRepository.findById(session.id) shouldBe session
            sessionEventPublisher.publishedEvents shouldBe
                listOf(
                    SessionEvent.SessionCreated(
                        session.id.value,
                        allowedUsers.map { it.value }.toSet()
                    )
                )
        }

        test("should delete a session") {
            val session = SessionFactory.create()
            sessionRepository.save(session)
            val command = SessionService.Command.DeleteSession(session.id)
            sessionService.deleteSession(command)
            sessionRepository.findById(session.id) shouldBe null
        }

        test("should throw an exception when trying to delete a non-existing session") {
            shouldThrow<SessionErrors.SessionNotFound> {
                val command =
                    SessionService.Command.DeleteSession(SessionId("non-existing-session"))
                sessionService.deleteSession(command)
            }
        }

        test("should add an allowed user to a session") {
            val session = SessionFactory.create()
            sessionRepository.save(session)
            val command = SessionService.Command.AddAllowedUser(session.id, john().id)
            sessionService.addAllowedUser(command)
            val updatedSession = sessionRepository.findById(session.id)!!
            updatedSession.allowedUsers() shouldBe setOf(john().id)
            sessionEventPublisher.publishedEvents shouldBe
                listOf(SessionEvent.AllowedUserAdded(session.id.value, john().id.value))
        }

        test(
            "should throw an exception when trying to add an allowed user to a non-existing session"
        ) {
            shouldThrow<SessionErrors.SessionNotFound> {
                val command =
                    SessionService.Command.AddAllowedUser(
                        SessionId("non-existing-session"),
                        john().id
                    )
                sessionService.addAllowedUser(command)
            }
        }

        test(
            "should throw an exception when trying to add an allowed user that is already allowed"
        ) {
            shouldThrow<SessionErrors.UserAlreadyAllowed> {
                val session = SessionFactory.fromAllowedUsers(setOf(john().id))
                sessionRepository.save(session)
                val command = SessionService.Command.AddAllowedUser(session.id, john().id)
                sessionService.addAllowedUser(command)
            }
        }

        test("should remove an allowed user from a session") {
            val session = SessionFactory.fromAllowedUsers(setOf(john().id))
            sessionRepository.save(session)
            val command = SessionService.Command.RemoveAllowedUser(session.id, john().id)
            sessionService.removeAllowedUser(command)
            val updatedSession = sessionRepository.findById(session.id)!!
            updatedSession.allowedUsers() shouldBe emptySet()
            sessionEventPublisher.publishedEvents shouldBe
                listOf(SessionEvent.AllowedUserRemoved(session.id.value, john().id.value))
        }

        test(
            "should throw an exception when trying to remove an allowed user from a non-existing session"
        ) {
            shouldThrow<SessionErrors.SessionNotFound> {
                val command =
                    SessionService.Command.RemoveAllowedUser(
                        SessionId("non-existing-session"),
                        john().id
                    )
                sessionService.removeAllowedUser(command)
            }
        }

        test(
            "should throw an exception when trying to remove an allowed user that is not allowed"
        ) {
            shouldThrow<SessionErrors.UserNotAllowed> {
                val session = SessionFactory.fromAllowedUsers(setOf(jane().id))
                sessionRepository.save(session)
                val command = SessionService.Command.RemoveAllowedUser(session.id, john().id)
                sessionService.removeAllowedUser(command)
            }
        }

        test("should allow to join a session") {
            val session = SessionFactory.fromAllowedUsers(setOf(john().id))
            sessionRepository.save(session)
            val command = SessionService.Command.JoinSession(session.id, john().id)
            sessionService.joinSession(command)
            val updatedSession = sessionRepository.findById(session.id)!!
            updatedSession.participants() shouldBe setOf(john().id)
            sessionEventPublisher.publishedEvents shouldBe
                listOf(SessionEvent.ParticipantJoined(session.id.value, john().id.value))
        }

        test("should throw an exception when trying to join a non-existing session") {
            shouldThrow<SessionErrors.SessionNotFound> {
                val command =
                    SessionService.Command.JoinSession(SessionId("non-existing-session"), john().id)
                sessionService.joinSession(command)
            }
        }

        test("should throw an exception when trying to join a session that is not allowed") {
            shouldThrow<SessionErrors.UserNotAllowed> {
                val session = SessionFactory.fromAllowedUsers(setOf(jane().id))
                sessionRepository.save(session)
                val command = SessionService.Command.JoinSession(session.id, john().id)
                sessionService.joinSession(command)
            }
        }

        test(
            "should throw an exception when trying to join a session that is already a participant"
        ) {
            shouldThrow<SessionErrors.UserAlreadyParticipant> {
                val session = SessionFactory.fromAllowedParticipants(setOf(john().id))
                sessionRepository.save(session)
                val command = SessionService.Command.JoinSession(session.id, john().id)
                sessionService.joinSession(command)
            }
        }

        test("should allow to leave a session") {
            val session = SessionFactory.fromAllowedParticipants(setOf(john().id))
            sessionRepository.save(session)
            val leaveCommand = SessionService.Command.LeaveSession(session.id, john().id)
            sessionService.leaveSession(leaveCommand)
            val updatedSessionAfterLeave = sessionRepository.findById(session.id)!!
            updatedSessionAfterLeave.participants() shouldBe emptySet()
            sessionEventPublisher.publishedEvents shouldBe
                listOf(SessionEvent.ParticipantLeft(session.id.value, john().id.value))
        }

        test("should throw an exception when trying to leave a non-existing session") {
            shouldThrow<SessionErrors.SessionNotFound> {
                val command =
                    SessionService.Command.LeaveSession(
                        SessionId("non-existing-session"),
                        john().id
                    )
                sessionService.leaveSession(command)
            }
        }

        test("should throw an exception when trying to leave a session that is not a participant") {
            shouldThrow<SessionErrors.UserNotParticipant> {
                val session = SessionFactory.fromAllowedUsers(setOf(john().id))
                sessionRepository.save(session)
                val command = SessionService.Command.LeaveSession(session.id, john().id)
                sessionService.leaveSession(command)
            }
        }

        test("should allow to get session participants") {
            val session = SessionFactory.fromAllowedUsers(setOf(john().id))
            sessionRepository.save(session)
            val participants = sessionService.getSessionParticipants(john().id, session.id)
            participants shouldBe emptySet()
        }

        test(
            "should throw an exception when trying to get session participants from a non-existing session"
        ) {
            shouldThrow<SessionErrors.SessionNotFound> {
                sessionService.getSessionParticipants(john().id, SessionId("non-existing-session"))
            }
        }

        test(
            "should throw an exception when trying to get session participants from a session that is not allowed"
        ) {
            shouldThrow<SessionErrors.UserNotAllowed> {
                val session = SessionFactory.fromAllowedUsers(setOf(jane().id))
                sessionRepository.save(session)
                sessionService.getSessionParticipants(john().id, session.id)
            }
        }
    })
