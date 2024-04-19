package piperkt.services.friendships.application

import piperkt.common.Repository
import piperkt.common.id.FriendshipRequestId
import piperkt.services.friendships.domain.FriendshipRequest

interface FriendshipRequestRepository : Repository<FriendshipRequestId, FriendshipRequest> {
    fun findByUserFriendshipRequests(user: String): List<FriendshipRequest>

    fun findByFriendshipRequest(from: String, to: String): FriendshipRequest?
}
