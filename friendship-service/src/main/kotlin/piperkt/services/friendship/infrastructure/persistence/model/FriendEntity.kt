package piperkt.services.friendship.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

@MappedEntity
data class FriendEntity(
    @Id val userId: String,
    val friends: List<String>,
    val friendRequests: List<String>,
    val pendingFriendRequests: List<String>
)

@MongoRepository interface FriensModelRepository : CrudRepository<FriendEntity, String>
