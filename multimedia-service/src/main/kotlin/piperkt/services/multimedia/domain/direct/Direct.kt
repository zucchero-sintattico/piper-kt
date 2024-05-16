package piperkt.services.multimedia.domain.direct

import piperkt.common.AggregateRoot
import piperkt.common.UUIDEntityId
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.user.Username

class DirectId(value: String = newId()) : UUIDEntityId(value)

class Direct(
    override val id: DirectId = DirectId(),
    val users: Set<Username>,
    val sessionId: SessionId
) : AggregateRoot<DirectId>(id)
