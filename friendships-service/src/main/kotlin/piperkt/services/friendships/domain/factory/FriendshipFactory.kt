package piperkt.services.friendships.domain.factory

import piperkt.common.id.FriendshipId
import piperkt.services.friendships.domain.Friendship

object FriendshipFactory {

    fun createFriendship(users: Set<String>, id: String = FriendshipId().value) =
        Friendship(users = users, id = FriendshipId(id))
}
