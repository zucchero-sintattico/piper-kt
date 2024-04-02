package piperkt.services.multimedia.domain.session

import java.util.*
import piperkt.services.multimedia.domain.EntityId

class SessionId(value: String = UUID.randomUUID().toString()) : EntityId<String>(value)
