package piperkt.services.friendships.application

import piperkt.common.Repository
import piperkt.common.id.FriendshipAggregateId
import piperkt.services.friendships.domain.Friendship
import piperkt.services.friendships.domain.FriendshipAggregate
import piperkt.services.friendships.domain.FriendshipRequest

interface FriendshipAggregateRepository : Repository<FriendshipAggregateId, FriendshipAggregate> {

    fun findUserFriendshipRequests(user: String): List<FriendshipRequest>

    fun findFriendshipRequest(from: String, to: String): FriendshipRequest?

    fun findUserFriendships(user: String): List<Friendship>

    fun findAll(): List<FriendshipAggregate>
}
