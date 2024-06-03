package piperkt.services.servers.domain

import piperkt.common.ddd.UUIDEntityId

class ChannelMessageId(value: String = newId()) : UUIDEntityId(value)
