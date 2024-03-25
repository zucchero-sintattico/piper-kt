package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import piperkt.services.multimedia.domain.Session
import piperkt.services.multimedia.domain.SessionId
import piperkt.services.multimedia.domain.User
import piperkt.services.multimedia.domain.Username

@MappedEntity
data class SessionEntity(
    @Id @GeneratedValue val id: String? = null,
    val allowedUsers: List<String>,
    val participants: List<String>
) {
    fun toDomain() =
        Session(
            SessionId(id!!),
            allowedUsers.map { User(Username(it)) },
            participants.map { User(Username(it)) }
        )
}

@MongoRepository interface SessionEntityRepository : CrudRepository<SessionEntity, String>
