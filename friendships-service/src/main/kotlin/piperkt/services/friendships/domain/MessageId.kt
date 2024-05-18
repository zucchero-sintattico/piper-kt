package piperkt.services.friendships.domain

import piperkt.common.ddd.UUIDEntityId

class MessageId(value: String = newId()) : UUIDEntityId(value)
