package piperkt.services.friendships.domain

import piperkt.common.AggregateRoot
import piperkt.common.id.FriendshipId

class Friendship(
    val users: Set<String>,
    id: FriendshipId = FriendshipId(),
    val messages: MutableList<Message> = mutableListOf()
) : AggregateRoot<FriendshipId>(id) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Friendship) return false
        if (!super.equals(other)) return false

        if (users != other.users) return false
        if (messages != other.messages) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + users.hashCode()
        result = 31 * result + messages.hashCode()
        return result
    }

    fun addMessage(message: Message) {
        messages.add(message)
    }
}
