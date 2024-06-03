package piperkt.services.friendships.domain

import piperkt.common.ddd.AggregateRoot

class Friendship(
    id: FriendshipId = FriendshipId(),
    val users: Set<String> = mutableSetOf(),
    val directMessages: MutableList<DirectMessage> = mutableListOf(),
) : AggregateRoot<FriendshipId>(id) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Friendship) return false
        if (!super.equals(other)) return false

        if (users != other.users) return false
        if (directMessages != other.directMessages) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + users.hashCode()
        result = 31 * result + directMessages.hashCode()
        return result
    }

    fun addMessage(directMessage: DirectMessage) {
        directMessages.add(directMessage)
    }
}

fun FriendshipRequest.toFriendship(): Friendship {
    return Friendship(users = setOf(this.from, this.to))
}
