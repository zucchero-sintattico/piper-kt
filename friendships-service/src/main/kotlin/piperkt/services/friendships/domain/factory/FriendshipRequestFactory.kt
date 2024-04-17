package piperkt.services.friendships.domain.factory

import piperkt.services.friendships.domain.FriendshipRequest
import piperkt.services.friendships.domain.FriendshipRequestStatus

object FriendshipRequestFactory {

    fun createFriendshipRequest(from: String, to: String) =
        FriendshipRequest(from = from, to = to, status = FriendshipRequestStatus.PENDING)

    fun createAcceptedFriendshipRequest(from: String, to: String) =
        FriendshipRequest(from = from, to = to, status = FriendshipRequestStatus.ACCEPTED)

    fun createRejectedFriendshipRequest(from: String, to: String) =
        FriendshipRequest(from = from, to = to, status = FriendshipRequestStatus.REJECTED)
}
