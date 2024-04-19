package piperkt.services.friendships.application

import piperkt.common.Repository
import piperkt.common.id.FriendshipId
import piperkt.services.friendships.domain.Friendship

interface FriendshipRepository : Repository<FriendshipId, Friendship> {
    fun findByFriendship(from: String, to: String): Friendship?

    fun findByUserFriendships(user: String): List<Friendship>
}
