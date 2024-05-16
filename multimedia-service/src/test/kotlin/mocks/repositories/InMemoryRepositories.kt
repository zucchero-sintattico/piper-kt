package mocks.repositories

import piperkt.common.InMemoryRepository
import piperkt.services.multimedia.domain.direct.Direct
import piperkt.services.multimedia.domain.direct.DirectId
import piperkt.services.multimedia.domain.direct.DirectRepository
import piperkt.services.multimedia.domain.server.Server
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.server.ServerRepository
import piperkt.services.multimedia.domain.session.Session
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.domain.user.Username

class InMemorySessionRepository : InMemoryRepository<SessionId, Session>(), SessionRepository

class InMemoryServerRepository : InMemoryRepository<ServerId, Server>(), ServerRepository

class InMemoryDirectRepository : InMemoryRepository<DirectId, Direct>(), DirectRepository {
    override fun findByUsers(users: Set<Username>): Direct? {
        return entities.values.find { it.users == users }
    }
}
