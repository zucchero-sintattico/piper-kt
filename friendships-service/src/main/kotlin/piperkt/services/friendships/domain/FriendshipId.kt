package piperkt.services.friendships.domain

import piperkt.common.ddd.UUIDEntityId

class FriendshipId(value: String = newId()) : UUIDEntityId(value)
