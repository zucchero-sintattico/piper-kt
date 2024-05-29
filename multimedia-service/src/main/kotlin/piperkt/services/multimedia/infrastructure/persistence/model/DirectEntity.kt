package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

/** Entity for directs */
@MappedEntity
data class DirectEntity(
    @Id @GeneratedValue val id: String,
    val users: Set<String>,
    val sessionId: String,
)

@MongoRepository
interface DirectEntityRepository : CrudRepository<DirectEntity, String> {
    fun findByUsers(users: Set<String>): DirectEntity?
}
