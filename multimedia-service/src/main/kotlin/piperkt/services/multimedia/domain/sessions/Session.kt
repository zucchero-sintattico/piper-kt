package piperkt.services.multimedia.domain.sessions

import piperkt.services.multimedia.domain.users.User

class Session(
    val id: SessionId = SessionId.empty(),
    val allowedUsers: List<User> = emptyList(),
    val participants: List<User> = emptyList()
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Session

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}
