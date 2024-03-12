package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

@MappedEntity
data class SessionEntity(
    @Id @GeneratedValue val id: String? = null,
    val allowedUsers: List<String>,
    val participants: List<String>
)

@MongoRepository interface SessionEntityRepository : CrudRepository<SessionEntity, String>
