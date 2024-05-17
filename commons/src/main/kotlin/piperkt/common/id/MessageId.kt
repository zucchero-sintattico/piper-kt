package piperkt.common.id

import piperkt.common.ddd.UUIDEntityId

class MessageId(value: String = newId()) : UUIDEntityId(value)
