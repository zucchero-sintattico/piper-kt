package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import jakarta.validation.constraints.NotEmpty
import piperkt.services.multimedia.domain.server.Server
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.user.UserId

@MappedEntity
data class ServerEntity(
    @Id val id: String? = null,
    @NotEmpty val participants: List<String>,
) {
    fun toDomain() = Server(id = ServerId(id!!), members = participants.map { UserId(it) })
}

@MongoRepository interface ServerEntityRepository : CrudRepository<ServerEntity, String>
