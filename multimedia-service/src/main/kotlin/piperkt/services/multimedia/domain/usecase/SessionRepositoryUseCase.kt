package piperkt.services.multimedia.domain.usecase

import piperkt.services.multimedia.domain.sessions.SessionRepository

interface SessionRepositoryUseCase {
    val sessionRepository: SessionRepository
}
