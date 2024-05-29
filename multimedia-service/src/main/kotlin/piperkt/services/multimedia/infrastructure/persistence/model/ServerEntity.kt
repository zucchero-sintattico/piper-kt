package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import jakarta.validation.constraints.NotEmpty

/** Entity for channels */
@MappedEntity data class ChannelEntity(@Id val id: String, val sessionId: String)

/** Entity for servers */
@MappedEntity
data class ServerEntity(
    @Id val id: String,
    @NotEmpty val participants: List<String>,
    val channels: List<ChannelEntity> = emptyList(),
)

@MongoRepository interface ServerEntityRepository : CrudRepository<ServerEntity, String>
