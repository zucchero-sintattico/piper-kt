package piperkt.services.multimedia.interfaces.web.controllers

import base.Test
import data.UsersData.jane
import data.UsersData.john
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import mocks.repositories.InMemorySessionRepository
import org.junit.jupiter.api.assertThrows
import piperkt.services.multimedia.application.usecases.GetSessionParticipants
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.interfaces.web.api.GetSessionParticipantsApi

class GetSessionParticipantsControllerTest :
    Test.Unit,
    FunSpec({
        val sessionRepository = InMemorySessionRepository()
        val getUserInSessionApi =
            GetSessionParticipantsController(GetSessionParticipants(sessionRepository))

        beforeEach { sessionRepository.clear() }

        test("should return users when session exists") {
            val users = listOf(john(), jane()).map { it.id }
            val session = SessionFactory.withParticipants(users.toSet(), users.toSet())
            sessionRepository.save(session)
            val result = getUserInSessionApi(session.id.value)
            result shouldBe GetSessionParticipantsApi.Response(users.map { it.value }.toSet())
        }

        test("should throw SessionNotFound when session does not exist") {
            val fakeSessionId = SessionId("nonExistingSessionId")
            assertThrows<SessionErrors.SessionNotFound> { getUserInSessionApi(fakeSessionId.value) }
        }
    })
