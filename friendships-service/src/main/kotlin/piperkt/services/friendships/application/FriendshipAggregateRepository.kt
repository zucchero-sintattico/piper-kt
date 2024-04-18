package piperkt.services.friendships.application

import piperkt.common.Repository
import piperkt.common.id.FriendshipAggregateId
import piperkt.services.friendships.domain.FriendshipAggregate

interface FriendshipAggregateRepository : Repository<FriendshipAggregateId, FriendshipAggregate> {

    fun findByUserFriendshipRequests(user: String): List<FriendshipAggregate>

    fun findByFriendshipRequest(from: String, to: String): FriendshipAggregate?

    fun findByFriendship(from: String, to: String): FriendshipAggregate?

    fun findByUserFriendships(user: String): List<FriendshipAggregate>

    fun findAll(): List<FriendshipAggregate>
}
