package piperkt.services.friendships.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import piperkt.services.friendships.domain.Friendship
import piperkt.services.friendships.domain.FriendshipId

@MappedEntity
data class FriendshipEntity(
    @Id val id: String,
    val users: Set<String>,
    val messages: List<MessageEntity> = emptyList(),
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

@MongoRepository
interface FriendshipModelRepository : CrudRepository<FriendshipEntity, String> {
    fun findByUsersContains(user: String): List<FriendshipEntity>

    fun findByUsersContainsAndUsersContains(user1: String, user2: String): FriendshipEntity?
}
