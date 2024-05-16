package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import jakarta.validation.constraints.NotEmpty
import piperkt.services.multimedia.domain.server.Channel
import piperkt.services.multimedia.domain.server.ChannelId
import piperkt.services.multimedia.domain.server.Server
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.user.Username

@MappedEntity
data class ChannelEntity(@Id val id: String, val sessionId: String) {
    fun toDomain() = Channel(id = ChannelId(id), sessionId = SessionId(sessionId))

    companion object {
        fun fromDomain(channel: Channel) =
            ChannelEntity(id = channel.id.value, sessionId = channel.sessionId.value)
    }
}

@MappedEntity
data class ServerEntity(
    @Id val id: String,
    @NotEmpty val participants: List<String>,
    val channels: List<ChannelEntity> = emptyList(),
) {
    fun toDomain() =
        Server(
            id = ServerId(id),
            members = participants.map { Username(it) }.toSet(),
            channels = channels.map { it.toDomain() }.toSet()
        )

    companion object {
        fun fromDomain(server: Server) =
            ServerEntity(
                id = server.id.value,
                participants = server.members().map { it.value },
                channels = server.channels().map { ChannelEntity.fromDomain(it) }
            )
    }
}

@MongoRepository interface ServerEntityRepository : CrudRepository<ServerEntity, String>
