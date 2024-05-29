package piperkt.services.multimedia.presentation

import piperkt.services.multimedia.domain.direct.Direct
import piperkt.services.multimedia.domain.direct.DirectId
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.user.Username
import piperkt.services.multimedia.infrastructure.persistence.model.DirectEntity

/** Mapper for [Direct] and [DirectEntity] */
object DirectMapper {
    fun Direct.toEntity() =
        DirectEntity(
            id = id.value,
            users = users.map { it.value }.toSet(),
            sessionId = sessionId.value
        )

    fun DirectEntity.toDomain() =
        Direct(
            id = DirectId(id),
            users = users.map { Username(it) }.toSet(),
            sessionId = SessionId(sessionId)
        )
}
