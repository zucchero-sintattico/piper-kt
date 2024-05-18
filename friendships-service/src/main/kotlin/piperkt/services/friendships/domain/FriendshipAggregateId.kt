package piperkt.services.friendships.domain

import piperkt.common.ddd.UUIDEntityId

class FriendshipAggregateId(value: String = newId()) : UUIDEntityId(value)
