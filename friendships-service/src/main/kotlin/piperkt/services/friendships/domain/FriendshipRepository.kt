package piperkt.services.friendships.domain

import piperkt.common.ddd.Repository

interface FriendshipRepository : Repository<FriendshipId, Friendship> {
    fun findByMembers(from: String, to: String): Friendship?

    fun findByUser(user: String): List<Friendship>
}
