package piperkt.services.multimedia.domain

data class Username(val value: String)

class User(
    val username: Username,
) {

    companion object {
        fun fromUsername(value: String): User {
            return User(Username(value))
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        return username == other.username
    }

    override fun hashCode(): Int {
        return username.hashCode()
    }
}
