package piperkt.services.friendships.application

import piperkt.common.ddd.Repository
import piperkt.services.friendships.domain.Friendship
import piperkt.services.friendships.domain.FriendshipId

interface FriendshipRepository : Repository<FriendshipId, Friendship> {
    fun findByMembers(from: String, to: String): Friendship?

    fun findByUser(user: String): List<Friendship>
}
