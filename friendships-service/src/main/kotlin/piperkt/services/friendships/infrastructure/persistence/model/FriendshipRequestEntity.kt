package piperkt.services.friendships.infrastructure.persistence.model

import io.micronaut.data.annotation.MappedEntity
import piperkt.services.friendships.domain.FriendshipRequest
import piperkt.services.friendships.domain.FriendshipRequestStatus

@MappedEntity
class FriendShipRequestEntity(
    val fromUser: String,
    val toUser: String,
    val status: String = "PENDING"
) {
    companion object {
        fun fromDomain(fromUser: String, toUser: String, status: FriendshipRequestStatus) =
            FriendShipRequestEntity(fromUser = fromUser, toUser = toUser, status = status.name)
    }
}

fun FriendShipRequestEntity.toDomain() =
    FriendshipRequest(
        from = fromUser,
        to = toUser,
        status = FriendshipRequestStatus.valueOf(status)
    )
