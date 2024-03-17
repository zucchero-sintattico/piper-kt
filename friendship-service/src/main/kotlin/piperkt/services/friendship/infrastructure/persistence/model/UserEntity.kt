package piperkt.services.friendship.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

@MappedEntity
data class UserEntity(
    @Id val username: String,
    val friends: List<String>,
    val friendRequests: List<String>,
    val pendingFriendRequests: List<String>
)

@MongoRepository interface FriendsModelRepository : CrudRepository<UserEntity, String>
