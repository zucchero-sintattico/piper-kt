package piperkt.services.friendship.domain

data class User(
    val username: String,
    val friends: List<User>,
    val friendRequests: List<User>,
    val pendingFriendRequests: List<User>
)
