package piperkt.services.multimedia.interfaces.web

import base.UnitTest
import data.UsersData.jane
import data.UsersData.john
import io.kotest.matchers.shouldBe
import java.security.Principal
import mocks.publishers.MockedSessionEventPublisher
import mocks.repositories.InMemoryDirectRepository
import mocks.repositories.InMemoryServerRepository
import mocks.repositories.InMemorySessionRepository
import org.junit.jupiter.api.assertThrows
import piperkt.services.multimedia.application.session.SessionService
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionId

fun String.toPrincipal() = Principal { this }

class GetSessionParticipantsControllerTest :
    UnitTest.FunSpec({
        val sessionRepository = InMemorySessionRepository()
        val serverRepository = InMemoryServerRepository()
        val directRepository = InMemoryDirectRepository()
        val sessionService =
            SessionService(
                sessionRepository,
                serverRepository,
                directRepository,
                MockedSessionEventPublisher()
            )
        val getSessionParticipantsController = GetSessionParticipantsController(sessionService)

        beforeEach { sessionRepository.clear() }

        test("should return users when session exists") {
            val users = setOf(john().id, jane().id)
            val session = SessionFactory.fromAllowedParticipants(users)
            sessionRepository.save(session)
            val result =
                getSessionParticipantsController.get(
                    john().id.value.toPrincipal(),
                    session.id.value
                )
            result shouldBe
                GetSessionParticipantsController.Response(users.map { it.value }.toSet())
        }

        test("should throw SessionNotFound when session does not exist") {
            val fakeSessionId = SessionId("nonExistingSessionId")
            assertThrows<SessionErrors.SessionNotFound> {
                getSessionParticipantsController.get(
                    john().id.value.toPrincipal(),
                    fakeSessionId.value
                )
            }
        }
    })
