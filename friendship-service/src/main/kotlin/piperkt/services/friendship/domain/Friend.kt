package piperkt.services.friendship.domain

data class Friend(
    val username: String,
    val friends: List<String>,
    val friendRequests: List<String>,
    val pendingFriendRequests: List<String>
)
