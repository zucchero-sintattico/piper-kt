package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import org.bson.types.ObjectId

@MappedEntity
data class DirectEntity(
    @Id val id: ObjectId? = null,
    var users: Set<String>,
    var sessionId: String
)

@MongoRepository
interface DirectEntityRepository : CrudRepository<DirectEntity, ObjectId> {
    fun findByUsers(users: Set<String>): DirectEntity?
}
