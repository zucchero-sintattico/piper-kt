package piperkt.services.users.domain.user

import piperkt.common.AggregateRoot
import piperkt.services.users.domain.friends.FriendRequest

class User(
    val username: Username,
    var password: String = "",
    var description: String = "",
    var profilePicture: ByteArray = ByteArray(0),
    var friends: List<Username> = emptyList(),
    var friendRequests: List<FriendRequest> = emptyList(),
    var sentFriendRequests: List<FriendRequest> = emptyList()
) : AggregateRoot<Username>(username) {

    fun updateDescription(description: String) {
        this.description = description
    }

    fun updateProfilePicture(profilePicture: ByteArray) {
        this.profilePicture = profilePicture
    }

    fun addFriend(friend: Username) {
        friends = friends + friend
    }

    fun removeFriend(friend: Username) {
        friends = friends - friend
    }

    fun addFriendRequest(friendRequest: FriendRequest) {
        friendRequests = friendRequests + friendRequest
    }

    fun removeFriendRequest(friendRequest: FriendRequest) {
        friendRequests = friendRequests - friendRequest
    }

    fun addSentFriendRequest(friendRequest: FriendRequest) {
        sentFriendRequests = sentFriendRequests + friendRequest
    }

    fun removeSentFriendRequest(friendRequest: FriendRequest) {
        sentFriendRequests = sentFriendRequests - friendRequest
    }
}
