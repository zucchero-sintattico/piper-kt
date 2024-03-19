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
        val user = friendsModelRepository.findById(username).orElse(null)
        return user?.friendRequests ?: emptyList()
    }

    override fun sendFriendRequest(username: String, friendUsername: String) {
        val user = friendsModelRepository.findById(username).orElse(null)
        user?.friendRequests?.add(friendUsername)
        friendsModelRepository.save(user)
    }

    override fun acceptFriendRequest(username: String, friendUsername: String) {
        val user = friendsModelRepository.findById(username).orElse(null)
        user?.friends?.add(friendUsername)
        friendsModelRepository.save(user)

        val friend = friendsModelRepository.findById(friendUsername).orElse(null)
        friend?.friends?.add(username)
        friendsModelRepository.save(friend)

        friend?.friendRequests?.remove(username)
    }

    override fun denyFriendRequest(username: String, friendUsername: String) {
        val user = friendsModelRepository.findById(username).orElse(null)
        user?.friendRequests?.remove(friendUsername)
        friendsModelRepository.save(user)
    }
}
