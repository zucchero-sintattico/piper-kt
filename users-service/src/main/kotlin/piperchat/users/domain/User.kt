package piperchat.users.domain

import piperkt.services.commons.domain.id.UserId

interface User {
    val id: UserId
    val username: String
    val email: String
    val description: String?
    val photoUrl: String?
    val friends: List<UserId>
}

interface FriendRequest {
    val id: String
    val from: User
    val to: User
    val status: FriendRequestStatus
}

enum class FriendRequestStatus {
    PENDING,
    ACCEPTED,
    REJECTED
}
