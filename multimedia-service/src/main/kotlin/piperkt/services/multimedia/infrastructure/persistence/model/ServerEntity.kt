package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import jakarta.validation.constraints.NotEmpty

@MappedEntity
data class ServerEntity(
    @Id val id: String? = null,
    @NotEmpty val participants: List<String>,
    @NotEmpty val sessionId: String
)

@MongoRepository interface ServerEntityRepository : CrudRepository<ServerEntity, String>
