package piperkt.services.friendships.domain

import piperkt.common.ValueObject

enum class FriendshipStatus {
    PENDING,
    ACCEPTED,
    REJECTED
}

data class FriendshipRequest(
    val sender: String,
    val receiver: String,
    val status: FriendshipStatus
) : ValueObject
