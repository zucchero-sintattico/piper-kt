package piperkt.services.multimedia.domain.session

import piperkt.services.multimedia.domain.UUIDEntityId

class SessionId(value: String = generateId()) : UUIDEntityId(value)
