package piperkt.services.multimedia.presentation

import piperkt.services.multimedia.domain.server.Server
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.user.Username
import piperkt.services.multimedia.infrastructure.persistence.model.ServerEntity
import piperkt.services.multimedia.presentation.ChannelMapper.toDomain
import piperkt.services.multimedia.presentation.ChannelMapper.toEntity

/** Mapper for [Server] and [ServerEntity] */
object ServerMapper {
    fun Server.toEntity() =
        ServerEntity(
            id = id.value,
            participants = members().map { it.value },
            channels = channels().map { it.toEntity() }
        )

    fun ServerEntity.toDomain() =
        Server(
            id = ServerId(id),
            members = participants.map { Username(it) }.toSet(),
            channels = channels.map { it.toDomain() }.toSet()
        )
}
