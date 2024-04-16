package piperkt.services.friendships.domain

import piperkt.common.AggregateRoot
import piperkt.common.id.FriendshipId

class FriendshipAggregate(
    id: FriendshipId = FriendshipId(),
    val friendshipRequest: FriendshipRequest,
    val friendship: Friendship? = null
) : AggregateRoot<FriendshipId>(id) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is FriendshipAggregate) return false
        if (!super.equals(other)) return false

        if (friendshipRequest != other.friendshipRequest) return false
        if (friendship != other.friendship) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + friendshipRequest.hashCode()
        result = 31 * result + friendship.hashCode()
        return result
    }
}
