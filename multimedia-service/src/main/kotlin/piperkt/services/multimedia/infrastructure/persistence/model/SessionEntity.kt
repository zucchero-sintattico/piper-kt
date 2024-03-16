package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import piperkt.services.multimedia.domain.sessions.Session
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.UserId

@MappedEntity
data class SessionEntity(
    @Id @GeneratedValue val id: String? = null,
    val allowedUsers: List<String>,
    val participants: List<String>
) {
    fun toDomain() =
        Session(
            SessionId(id!!),
            allowedUsers.map { User(UserId(it)) },
            participants.map { User(UserId(it)) }
        )
}

@MongoRepository interface SessionEntityRepository : CrudRepository<SessionEntity, String>
