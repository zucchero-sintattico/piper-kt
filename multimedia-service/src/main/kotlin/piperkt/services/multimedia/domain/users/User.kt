package piperkt.services.multimedia.domain.users

data class User(
    val username: Username,
) {
    companion object {
        fun fromUsername(value: String): User {
            return User(Username(value))
        }
    }
}
