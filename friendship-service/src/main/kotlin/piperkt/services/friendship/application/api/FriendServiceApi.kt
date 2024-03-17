package piperkt.services.friendship.application.api

interface FriendServiceApi {
    fun getFriends(username: String): List<String>

    fun getFriendRequests(username: String): List<String>

    fun sendFriendRequest(username: String, friendUsername: String)

    fun acceptFriendRequest(username: String, friendUsername: String)

    fun denyFriendRequest(username: String, friendUsername: String)
}
