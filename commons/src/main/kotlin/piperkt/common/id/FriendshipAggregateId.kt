package piperkt.common.id

import piperkt.common.ddd.UUIDEntityId

class FriendshipAggregateId(value: String = newId()) : UUIDEntityId(value)
