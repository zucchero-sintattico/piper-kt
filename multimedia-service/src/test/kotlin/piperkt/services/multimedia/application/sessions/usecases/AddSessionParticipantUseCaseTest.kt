package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.SessionsData
import io.kotest.matchers.shouldBe
import mocks.MockedEventPublisher
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import piperkt.services.multimedia.domain.sessions.SessionRepository
import piperkt.services.multimedia.domain.users.User

class AddSessionParticipantUseCaseTest :
    Test.Unit.FunSpec({
        context("a session") {
            val session = SessionsData.simpleSession()
            val sessionRepository = mock<SessionRepository>()
            val eventPublisher = MockedEventPublisher()
            val addSessionParticipantUseCase =
                AddSessionParticipantUseCase(sessionRepository, eventPublisher)

            beforeEach { eventPublisher.clear() }

            test("should allow to add a participant to the session") {
                val user = User.fromUsername("user")
                whenever(sessionRepository.findById(session.id)).thenReturn(session)
                whenever(sessionRepository.addParticipant(session.id, user)).thenReturn(true)
                val result =
                    addSessionParticipantUseCase.handle(
                        AddSessionParticipantUseCase.Command(session.id.value, user.username.value)
                    )
                result.isSuccess shouldBe true
                verify(sessionRepository).addParticipant(session.id, user)
                eventPublisher.publishedEvents shouldBe
                    listOf(AddSessionParticipantUseCase.Events.ParticipantAdded(session.id, user))
            }

            test("should return SessionNotFound error if session does not exist") {
                val user = User.fromUsername("user")
                whenever(sessionRepository.findById(session.id)).thenReturn(null)
                val result =
                    addSessionParticipantUseCase.handle(
                        AddSessionParticipantUseCase.Command(session.id.value, user.username.value)
                    )
                result.isFailure shouldBe true
                result.exceptionOrNull() shouldBe
                    AddSessionParticipantUseCase.Errors.SessionNotFound(session.id.value)
            }
        }
    })
