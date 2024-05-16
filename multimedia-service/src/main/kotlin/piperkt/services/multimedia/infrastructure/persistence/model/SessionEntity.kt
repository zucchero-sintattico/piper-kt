package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import piperkt.services.multimedia.domain.session.Session
import piperkt.services.multimedia.domain.session.SessionFactory
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.user.Username

@MappedEntity
data class SessionEntity(
    @Id val id: String,
    val allowedUsers: List<String>,
    val participants: List<String>,
) {
    fun toDomain() =
        SessionFactory.create(
            SessionId(id),
            allowedUsers.map { Username(it) }.toSet(),
            participants.map { Username(it) }.toSet()
        )

    companion object {
        fun fromDomain(session: Session) =
            SessionEntity(
                id = session.id.value,
                allowedUsers = session.allowedUsers().map { it.value },
                participants = session.participants().map { it.value }
            )
    }
}

@MongoRepository interface SessionEntityRepository : CrudRepository<SessionEntity, String>
