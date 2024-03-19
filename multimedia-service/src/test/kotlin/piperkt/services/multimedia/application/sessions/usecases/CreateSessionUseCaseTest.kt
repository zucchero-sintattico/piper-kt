package piperkt.services.multimedia.application.sessions.usecases

import base.Test
import data.UsersData
import io.kotest.matchers.shouldBe
import mocks.MockedEventPublisher
import mocks.repositories.InMemorySessionRepository
import piperkt.services.multimedia.application.sessions.usecases.CreateSessionUseCase.Command
import piperkt.services.multimedia.application.sessions.usecases.CreateSessionUseCase.Events.SessionCreated
import piperkt.services.multimedia.application.success

class CreateSessionUseCaseTest :
    Test.Unit.FunSpec({
        val sessionRepository = InMemorySessionRepository()
        val eventPublisher = MockedEventPublisher()
        val createSessionUseCase = CreateSessionUseCase(sessionRepository, eventPublisher)

        beforeEach {
            eventPublisher.clear()
            sessionRepository.clear()
        }

        test("should create session and publish SessionCreated event") {
            val users = listOf(UsersData.john())
            val result = createSessionUseCase(Command(users.map { it.username.value }))
            result shouldBe success()
            eventPublisher.publishedEvents shouldBe
                listOf(SessionCreated(sessionRepository.sessions.values.first()))
        }
    })
