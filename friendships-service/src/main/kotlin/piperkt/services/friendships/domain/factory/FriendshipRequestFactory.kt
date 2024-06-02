package piperkt.services.friendships.domain.factory

import piperkt.common.ddd.Factory
import piperkt.services.friendships.domain.FriendshipRequest

object FriendshipRequestFactory : Factory<FriendshipRequest> {

    fun createFriendshipRequest(from: String, to: String) = FriendshipRequest(from = from, to = to)
}
