package piperkt.services.friendships.domain

import piperkt.common.ddd.AggregateRoot

class FriendshipRequest(
    override val id: FriendshipRequestId = FriendshipRequestId(),
    val from: String,
    val to: String,
) : AggregateRoot<FriendshipRequestId>(id) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FriendshipRequest) return false
        if (!super.equals(other)) return false

        if (id != other.id) return false
        if (from != other.from) return false
        if (to != other.to) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + id.hashCode()
        result = 31 * result + from.hashCode()
        result = 31 * result + to.hashCode()
        return result
    }
}
