package piperkt.services.friendship.infrastructure.persistence.repository

import jakarta.inject.Named
import piperkt.services.friendship.domain.Friend
import piperkt.services.friendship.domain.FriendRepository

@Named("inMemory")
class InMemoryRepository : FriendRepository {
    val friends = mutableListOf<Friend>()

    override fun getFriends(username: String): List<String> {
        TODO()
    }

    override fun getFriendRequests(username: String): List<String> {
        TODO()
    }

    override fun sendFriendRequest(username: String, friendUsername: String) {
        TODO()
    }

    override fun acceptFriendRequest(username: String, friendUsername: String) {
        TODO()
    }

    override fun denyFriendRequest(username: String, friendUsername: String) {
        TODO()
    }
}
