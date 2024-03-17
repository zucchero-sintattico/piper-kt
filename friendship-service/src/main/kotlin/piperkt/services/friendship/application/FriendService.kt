package piperkt.services.friendship.application

import piperkt.services.friendship.application.api.FriendServiceApi
import piperkt.services.friendship.domain.Friend
import piperkt.services.friendship.domain.FriendRepository

open class FriendService(private val userRepository: FriendRepository) : FriendServiceApi {

    override fun getFriends(username: String): List<Friend> {
        return userRepository.getFriends(username)
    }

    override fun getFriendRequests(username: String): List<Friend> {
        return userRepository.getFriendRequests(username)
    }

    override fun sendFriendRequest(username: String, friendUsername: String) {
        userRepository.sendFriendRequest(username, friendUsername)
    }

    override fun acceptFriendRequest(username: String, friendUsername: String) {
        userRepository.acceptFriendRequest(username, friendUsername)
    }

    override fun denyFriendRequest(username: String, friendUsername: String) {
        userRepository.denyFriendRequest(username, friendUsername)
    }
}
