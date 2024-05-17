package piperkt.services.multimedia.domain.session

import piperkt.common.ddd.Repository

interface SessionRepository : Repository<SessionId, Session>
