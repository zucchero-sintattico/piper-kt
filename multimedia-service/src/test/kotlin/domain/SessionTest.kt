package domain

import base.Test
import data.Users
import io.kotest.matchers.shouldBe
import piperkt.services.multimedia.domain.sessions.Session
import piperkt.services.multimedia.domain.sessions.SessionId

class SessionTest :
    Test.Unit.FunSpec({
        context("Session") {
            val users = listOf(Users.john(), Users.jane())
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
                        participants = listOf(Users.john())
                    )
                session.id shouldBe sessionId
                session.allowedUsers shouldBe users
                session.participants shouldBe listOf(Users.john())
            }

            test("should be able to add an allowed user") {
                val session = Session(sessionId)
                session.addAllowedUser(Users.john())
                session.allowedUsers shouldBe listOf(Users.john())
            }

            test("should be able to remove an allowed user") {
                val session = Session(id = sessionId, allowedUsers = users)
                session.removeAllowedUser(Users.john())
                session.allowedUsers shouldBe listOf(Users.jane())
            }
        }
    })
