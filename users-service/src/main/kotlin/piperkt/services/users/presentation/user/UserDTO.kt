package piperkt.services.users.presentation.user

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class UserDTO(
    val username: String,
    val description: String = "",
    val profilePicture: ByteArray = byteArrayOf()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as UserDTO

        if (username != other.username) return false
        if (description != other.description) return false
        if (!profilePicture.contentEquals(other.profilePicture)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + profilePicture.contentHashCode()
        return result
    }
}
