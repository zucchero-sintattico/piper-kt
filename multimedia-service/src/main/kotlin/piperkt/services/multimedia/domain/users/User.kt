package piperkt.services.multimedia.domain.users

class User(
    val username: UserId,
) {
    companion object {
        fun fromUserId(userId: UserId) = User(userId)
    }
}
