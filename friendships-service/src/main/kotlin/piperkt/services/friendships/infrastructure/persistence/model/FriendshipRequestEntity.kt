package piperkt.services.friendships.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import piperkt.common.id.FriendshipRequestId
import piperkt.services.friendships.domain.FriendshipRequest

@MappedEntity
data class FriendshipRequestEntity(
    @Id val id: String,
    val fromUser: String,
    val toUser: String,
) {
    companion object {
        fun fromDomain(friendshipRequest: FriendshipRequest) =
            FriendshipRequestEntity(
                id = friendshipRequest.id.value,
                fromUser = friendshipRequest.from,
                toUser = friendshipRequest.to
            )
    }
}

fun FriendshipRequestEntity.toDomain() =
    FriendshipRequest(from = fromUser, to = toUser, id = FriendshipRequestId(id))

@MongoRepository
interface FriendshipRequestModelRepository : CrudRepository<FriendshipRequestEntity, String> {
    fun findByFromUser(fromUser: String): List<FriendshipRequestEntity>

    fun findByToUser(toUser: String): List<FriendshipRequestEntity>

    fun findByFromUserAndToUser(fromUser: String, toUser: String): FriendshipRequestEntity?
}
