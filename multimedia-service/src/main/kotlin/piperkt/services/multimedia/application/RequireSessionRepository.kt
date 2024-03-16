package piperkt.services.multimedia.application

import piperkt.services.multimedia.domain.sessions.SessionRepository

interface RequireSessionRepository {
    val sessionRepository: SessionRepository
}
