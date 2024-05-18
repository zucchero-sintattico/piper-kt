package piperkt.services.friendships.application

import piperkt.common.ddd.Repository
import piperkt.services.friendships.domain.FriendshipRequest
import piperkt.services.friendships.domain.FriendshipRequestId

interface FriendshipRequestRepository : Repository<FriendshipRequestId, FriendshipRequest> {
    fun findByReceiver(user: String): List<FriendshipRequest>

    fun findByMembers(from: String, to: String): FriendshipRequest?
}
