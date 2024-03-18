package domain

import base.Test
import data.UsersData
import io.kotest.matchers.shouldBe
import piperkt.services.multimedia.domain.sessions.Session
import piperkt.services.multimedia.domain.sessions.SessionId

class SessionTest :
    Test.Unit.FunSpec({
        context("a Session") {
            val users = listOf(UsersData.john(), UsersData.jane())
            val sessionId = SessionId("SessionId")

            test("should be able to create a session given an Id") {
                val session = Session(sessionId)
                session.id shouldBe sessionId
                session.allowedUsers shouldBe emptyList()
                session.participants shouldBe emptyList()
            }

            test("should be able to create a session given an Id and a list of allowed users") {
                val session = Session(id = sessionId, allowedUsers = users)
                session.id shouldBe sessionId
                session.allowedUsers shouldBe users
                session.participants shouldBe emptyList()
            }

            test(
                "should be able to create a session given an Id, a list of allowed users and a list of participants"
            ) {
                val session =
                    Session(
                        id = sessionId,
                        allowedUsers = users,
                        participants = listOf(UsersData.john())
                    )
                session.id shouldBe sessionId
                session.allowedUsers shouldBe users
                session.participants shouldBe listOf(UsersData.john())
            }
        }

        context("two Sessions") {
            val sessionId = SessionId("SessionId")
            val session1 = Session(sessionId)
            val session2 = Session(sessionId, allowedUsers = listOf(UsersData.john()))

            test("should be equal if their ids are equal") { session1 shouldBe session2 }
        }
    })
