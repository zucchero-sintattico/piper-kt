package piperkt.services.friendships.domain

enum class FriendshipRequestStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
}

data class FriendshipRequest(
    val from: String,
    val to: String,
    var status: FriendshipRequestStatus,
)
