package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import jakarta.validation.constraints.NotEmpty
import piperkt.services.multimedia.domain.server.Server
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.user.Username

@MappedEntity
data class ServerEntity(
    @Id val id: String,
    @NotEmpty val participants: List<String>,
) {
    fun toDomain() = Server(id = ServerId(id), members = participants.map { Username(it) })

    companion object {
        fun fromDomain(server: Server) =
            ServerEntity(id = server.id.value, participants = server.members().map { it.value })
    }
}

@MongoRepository interface ServerEntityRepository : CrudRepository<ServerEntity, String>
