package piperkt.services.friendship.infrastructure.persistence.repository

import jakarta.inject.Singleton
import piperkt.services.friendship.domain.FriendRepository
import piperkt.services.friendship.infrastructure.persistence.model.FriendsModelRepository

@Singleton
class FriendRepositoryImpl(private val friendsModelRepository: FriendsModelRepository) :
    FriendRepository {

    override fun getFriends(username: String): List<String> {
        val user = friendsModelRepository.findById(username).orElse(null)
        return user?.friends ?: emptyList()
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
