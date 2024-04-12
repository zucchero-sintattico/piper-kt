package piperkt.services.multimedia.domain.direct

import piperkt.common.AggregateRoot
import piperkt.services.multimedia.domain.session.SessionId

class Direct(override val id: DirectId, val sessionId: SessionId) : AggregateRoot<DirectId>(id)