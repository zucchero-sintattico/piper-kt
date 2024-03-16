package piperkt.services.friendship.infrastructure.persistence.repository

import jakarta.inject.Named
import piperkt.services.friendship.domain.Friend
import piperkt.services.friendship.domain.FriendRepository

@Named("inMemory")
class InMemoryRepository : FriendRepository {
    val friends = mutableListOf<Friend>()

    override fun sendFriendRequest(sender: Friend, receiver: Friend): Nothing {
        TODO()
    }
}
