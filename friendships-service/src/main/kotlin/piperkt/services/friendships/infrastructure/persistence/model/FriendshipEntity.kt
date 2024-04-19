package piperkt.services.friendships.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import piperkt.common.id.FriendshipId
import piperkt.services.friendships.domain.Friendship

class FriendshipEntity(
    @Id val id: String,
    val users: Set<String>,
    val messages: List<MessageEntity> = emptyList()
) {
    companion object {
        fun fromDomain(friendship: Friendship) =
            FriendshipEntity(
                id = friendship.id.value,
                users = friendship.users,
                messages = friendship.messages.map { MessageEntity.fromDomain(it) }
            )
    }
}

fun FriendshipEntity.toDomain() =
    Friendship(
        id = FriendshipId(id),
        users = users,
        messages = messages.map { it.toDomain() }.toMutableList()
    )
