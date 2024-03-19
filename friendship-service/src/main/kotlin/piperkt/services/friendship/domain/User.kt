package piperkt.services.friendship.domain

data class User(
    val username: String,
    val friends: MutableList<User>,
    val friendRequests: MutableList<User>,
    val pendingFriendRequests: MutableList<User>
)
