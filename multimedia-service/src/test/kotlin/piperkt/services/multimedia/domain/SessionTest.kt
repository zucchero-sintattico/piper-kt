package piperkt.services.multimedia.domain

import base.UnitTest
import data.UsersData.jane
import data.UsersData.john
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.user.Username

class SessionTest :
    UnitTest.FunSpec({
        context("a Session") {
            val users = setOf(john().id, jane().id)

            test("should be able to create a session given an Id") {
                val session = SessionFactory.empty()
                session.allowedUsers() shouldBe emptySet()
                session.participants() shouldBe emptySet()
            }

            test("should be able to create a session given an Id and a list of allowed users") {
                val session = SessionFactory.fromAllowedUsers(users)
                session.allowedUsers() shouldBe users
                session.participants() shouldBe emptySet()
            }

            test(
                "should be able to create a session given an Id, a list of allowed users and a list of participants()"
            ) {
                val session = SessionFactory.fromAllowedParticipants(users)
                session.allowedUsers() shouldBe users
                session.participants() shouldBe users
            }

            test("should be able to add a participant") {
                val session = SessionFactory.fromAllowedUsers(users)
                session.addParticipant(john().id)
                session.participants() shouldBe setOf(john().id)
            }

            test("should be able to remove a participant") {
                val session = SessionFactory.fromAllowedParticipants(users)
                session.removeParticipant(john().id)
                session.participants() shouldBe setOf(jane().id)
            }

            test("should be able to add an allowed user") {
                val session = SessionFactory.empty()
                session.addAllowedUser(john().id)
                session.allowedUsers() shouldBe setOf(john().id)
            }

            test("should be able to remove an allowed user") {
                val session = SessionFactory.fromAllowedUsers(users)
                session.removeAllowedUser(john().id)
                session.allowedUsers() shouldBe setOf(jane().id)
            }

            test("should throw UserNotAllowed when adding a participant that is not allowed") {
                val session = SessionFactory.fromAllowedUsers(users)
                val exception =
                    shouldThrow<SessionErrors.UserNotAllowed> {
                        session.addParticipant(Username("notAllowed"))
                    }
                exception.sessionId shouldBe session.id
                exception.username shouldBe Username("notAllowed")
            }

            test(
                "should throw UserAlreadyParticipant when adding a participant that is already a participant"
            ) {
                val session = SessionFactory.fromAllowedParticipants(users)
                val exception =
                    shouldThrow<SessionErrors.UserAlreadyParticipant> {
                        session.addParticipant(john().id)
                    }
                exception.sessionId shouldBe session.id
                exception.username shouldBe john().id
            }

            test(
                "should throw UserNotParticipant when removing a participant that is not a participant"
            ) {
                val session = SessionFactory.fromAllowedParticipants(users)
                val exception =
                    shouldThrow<SessionErrors.UserNotParticipant> {
                        session.removeParticipant(Username("notParticipant"))
                    }
                exception.sessionId shouldBe session.id
                exception.username shouldBe Username("notParticipant")
            }

            test(
                "should throw UserAlreadyAllowed when adding an allowed user that is already allowed"
            ) {
                val session = SessionFactory.fromAllowedUsers(users)
                val exception =
                    shouldThrow<SessionErrors.UserAlreadyAllowed> {
                        session.addAllowedUser(john().id)
                    }
                exception.sessionId shouldBe session.id
                exception.username shouldBe john().id
            }

            test("should throw UserNotAllowed when removing an allowed user that is not allowed") {
                val session = SessionFactory.fromAllowedUsers(users)
                val exception =
                    shouldThrow<SessionErrors.UserNotAllowed> {
                        session.removeAllowedUser(Username("notAllowed"))
                    }
                exception.sessionId shouldBe session.id
                exception.username shouldBe Username("notAllowed")
            }
        }

        context("two Sessions") {
            val sessionId = SessionId("SessionId")
            val session1 = SessionFactory.create(sessionId)
            val session2 = SessionFactory.create(sessionId)

            test("should be equal if their ids are equal") { session1 shouldBe session2 }
        }
    })
