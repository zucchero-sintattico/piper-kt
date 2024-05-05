package piperkt.services.friendships.application

import piperkt.common.Repository
import piperkt.common.id.FriendshipId
import piperkt.services.friendships.domain.Friendship

interface FriendshipRepository : Repository<FriendshipId, Friendship> {
    fun findByMembers(from: String, to: String): Friendship?

    fun findByUser(user: String): List<Friendship>
}
