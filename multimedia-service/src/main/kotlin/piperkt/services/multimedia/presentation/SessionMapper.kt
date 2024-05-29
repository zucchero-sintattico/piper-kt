package piperkt.services.multimedia.presentation

import piperkt.services.multimedia.domain.session.Session
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.user.Username
import piperkt.services.multimedia.infrastructure.persistence.model.SessionEntity

/** Mapper for [Session] and [SessionEntity] */
object SessionMapper {
    fun Session.toEntity() =
        SessionEntity(
            id = id.value,
            allowedUsers = allowedUsers().map { it.value },
            participants = participants().map { it.value }
        )

    fun SessionEntity.toDomain() =
        SessionFactory.create(
            SessionId(id),
            allowedUsers.map { Username(it) }.toSet(),
            participants.map { Username(it) }.toSet()
        )
}
