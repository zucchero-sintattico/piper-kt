package piperkt.services.servers.domain

import piperkt.common.ddd.UUIDEntityId

class MessageId(value: String = newId()) : UUIDEntityId(value)
