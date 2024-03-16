package piperkt.services.friendship.domain

interface FriendRepository {
    fun sendFriendRequest(sender: Friend, receiver: Friend): Nothing
}
