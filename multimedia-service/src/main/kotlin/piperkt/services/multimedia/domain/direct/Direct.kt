package piperkt.services.multimedia.domain.direct

import piperkt.common.AggregateRoot
import piperkt.common.EntityId
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.user.Username

class DirectId(value: Set<Username>) : EntityId<Set<Username>>(value)

class Direct(override val id: DirectId, val sessionId: SessionId) : AggregateRoot<DirectId>(id)
