package piperkt.services.multimedia.presentation

import piperkt.services.multimedia.domain.server.Channel
import piperkt.services.multimedia.domain.server.ChannelId
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.infrastructure.persistence.model.ChannelEntity

/** Mapper for [Channel] and [ChannelEntity] */
object ChannelMapper {
    fun Channel.toEntity() = ChannelEntity(id = id.value, sessionId = sessionId.value)

    fun ChannelEntity.toDomain() = Channel(id = ChannelId(id), sessionId = SessionId(sessionId))
}
