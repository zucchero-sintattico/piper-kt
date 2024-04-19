package piperkt.services.friendships.domain.factory

import piperkt.services.friendships.domain.FriendshipRequest

object FriendshipRequestFactory {

    fun createFriendshipRequest(from: String, to: String) = FriendshipRequest(from = from, to = to)
}
