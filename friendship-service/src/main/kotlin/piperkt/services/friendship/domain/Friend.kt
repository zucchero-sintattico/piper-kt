package piperkt.services.friendship.domain

data class Friend(
    val userId: String,
    val friends: List<String>,
    val friendRequests: List<String>,
    val pendingFriendRequests: List<String>
)
