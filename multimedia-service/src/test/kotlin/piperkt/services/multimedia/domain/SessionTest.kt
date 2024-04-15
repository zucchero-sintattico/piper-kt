package piperkt.services.multimedia.domain

import base.UnitTest
import data.UsersData.jane
import data.UsersData.john
import io.kotest.matchers.shouldBe
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionId

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
        }

        context("two Sessions") {
            val sessionId = SessionId("SessionId")
            val session1 = SessionFactory.create(sessionId)
            val session2 = SessionFactory.create(sessionId)

            test("should be equal if their ids are equal") { session1 shouldBe session2 }
        }
    })
