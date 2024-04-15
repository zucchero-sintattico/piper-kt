package mocks.repositories

import piperkt.common.InMemoryRepository
import piperkt.services.multimedia.domain.session.Session
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.session.SessionRepository

class InMemorySessionRepository : InMemoryRepository<SessionId, Session>(), SessionRepository
