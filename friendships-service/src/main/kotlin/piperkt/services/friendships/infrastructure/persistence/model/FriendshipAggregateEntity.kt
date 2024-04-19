package piperkt.services.friendships.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import piperkt.common.id.FriendshipAggregateId
import piperkt.services.friendships.domain.FriendshipAggregate

@MappedEntity
data class FriendshipAggregateEntity(
    @Id val id: String,
    val friendshipRequest: FriendShipRequestEntity,
    val friendship: FriendshipEntity? = null
) {
    companion object {
        fun fromDomain(friendshipAggregate: FriendshipAggregate) =
            FriendshipAggregateEntity(
                id = friendshipAggregate.id.value,
                friendshipRequest =
                    FriendShipRequestEntity.fromDomain(
                        fromUser = friendshipAggregate.friendshipRequest.from,
                        toUser = friendshipAggregate.friendshipRequest.to,
                        status = friendshipAggregate.friendshipRequest.status
                    ),
                friendship = friendshipAggregate.friendship?.let { FriendshipEntity.fromDomain(it) }
            )
    }
}

fun FriendshipAggregateEntity.toDomain() =
    FriendshipAggregate(
        id = FriendshipAggregateId(id),
        friendshipRequest = friendshipRequest.toDomain(),
        friendship = friendship?.toDomain()
    )
