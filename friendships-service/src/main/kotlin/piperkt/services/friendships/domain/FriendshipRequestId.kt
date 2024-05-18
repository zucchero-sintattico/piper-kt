package piperkt.services.friendships.domain

import piperkt.common.ddd.UUIDEntityId

class FriendshipRequestId(value: String = newId()) : UUIDEntityId(value)
