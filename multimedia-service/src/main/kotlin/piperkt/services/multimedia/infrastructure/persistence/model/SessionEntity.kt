package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

/** Entity for sessions */
@MappedEntity
data class SessionEntity(
    @Id val id: String,
    val allowedUsers: List<String>,
    val participants: List<String>,
)

@MongoRepository interface SessionEntityRepository : CrudRepository<SessionEntity, String>
