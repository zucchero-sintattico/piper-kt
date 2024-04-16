package piperkt.services.users.infrastructure.persistence.model

import piperkt.services.users.domain.friends.FriendRequestStatus
import piperkt.services.users.domain.user.Username

enum class FriendRequestStatusEntity {
    PENDING,
    ACCEPTED,
    REJECTED
}

data class FriendRequestEntity(
    val from: String,
    val to: String,
    val status: FriendRequestStatusEntity
) {
    fun toDomain(): piperkt.services.users.domain.friends.FriendRequest {
        return piperkt.services.users.domain.friends.FriendRequest(
            from = Username(from),
            to = Username(to),
            status =
                when (status) {
                    FriendRequestStatusEntity.PENDING -> FriendRequestStatus.PENDING
                    FriendRequestStatusEntity.ACCEPTED -> FriendRequestStatus.ACCEPTED
                    FriendRequestStatusEntity.REJECTED -> FriendRequestStatus.REJECTED
                }
        )
    }

    companion object {
        fun fromDomain(
            friendRequest: piperkt.services.users.domain.friends.FriendRequest
        ): FriendRequestEntity {
            return FriendRequestEntity(
                from = friendRequest.from.value,
                to = friendRequest.to.value,
                status =
                    when (friendRequest.status) {
                        FriendRequestStatus.PENDING -> FriendRequestStatusEntity.PENDING
                        FriendRequestStatus.ACCEPTED -> FriendRequestStatusEntity.ACCEPTED
                        FriendRequestStatus.REJECTED -> FriendRequestStatusEntity.REJECTED
                    }
            )
        }
    }
}
