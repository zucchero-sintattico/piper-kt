package piperkt.services.friendships.domain

import piperkt.common.ddd.UUIDEntityId

class DirectMessageId(value: String = newId()) : UUIDEntityId(value)
