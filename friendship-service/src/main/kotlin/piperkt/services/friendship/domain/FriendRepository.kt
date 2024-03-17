package piperkt.services.friendship.domain

interface FriendRepository {
    fun getFriends(username: String): List<Friend>

    fun getFriendRequests(username: String): List<Friend>

    fun sendFriendRequest(username: String, friendUsername: String)

    fun acceptFriendRequest(username: String, friendUsername: String)

    fun denyFriendRequest(username: String, friendUsername: String)
}
