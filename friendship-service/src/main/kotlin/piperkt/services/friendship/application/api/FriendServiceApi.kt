package piperkt.services.friendship.application.api

import piperkt.services.friendship.domain.Friend

interface FriendServiceApi {
    fun getFriends(username: String): List<Friend>

    fun getFriendRequests(username: String): List<Friend>

    fun sendFriendRequest(username: String, friendUsername: String)

    fun acceptFriendRequest(username: String, friendUsername: String)

    fun denyFriendRequest(username: String, friendUsername: String)
}
