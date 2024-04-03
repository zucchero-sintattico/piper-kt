package piperkt.services.multimedia.domain.server

import piperkt.services.multimedia.domain.UUIDEntityId

class ChannelId(value: String = generateId()) : UUIDEntityId(value)
