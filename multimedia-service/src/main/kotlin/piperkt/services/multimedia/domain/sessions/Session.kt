package piperkt.services.multimedia.domain.sessions

import piperkt.services.multimedia.domain.users.User

class Session(
    val id: SessionId = SessionId.empty(),
    var allowedUsers: List<User> = emptyList(),
    var participants: List<User> = emptyList()
) {

    fun addAllowedUser(user: User) {
        allowedUsers += user
    }

    fun removeAllowedUser(user: User) {
        allowedUsers = allowedUsers.filter { it.username.value != user.username.value }
    }

    fun addParticipant(user: User) {
        this.allowedUsers
            .find { it.username.value == user.username.value }
            ?.let { participants += user }
    }

    fun removeParticipant(user: User) {
        participants = participants.filter { it.username.value != user.username.value }
    }

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
