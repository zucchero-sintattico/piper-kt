package piperkt.services.servers.domain

import piperkt.common.ddd.UUIDEntityId

class ChannelId(value: String = newId()) : UUIDEntityId(value)
