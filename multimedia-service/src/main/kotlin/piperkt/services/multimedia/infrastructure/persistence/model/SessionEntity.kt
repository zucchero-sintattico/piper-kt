package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import piperkt.services.multimedia.domain.session.Session
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.user.UserId

@MappedEntity
data class SessionEntity(
    @Id @GeneratedValue val id: String? = null,
    val allowedUsers: List<String>,
    val participants: List<String>
) {
    fun toDomain() =
        Session(
            SessionId(id!!),
            allowedUsers.map { UserId(it) }.toList(),
            participants.map { UserId(it) }.toList()
        )
}

@MongoRepository interface SessionEntityRepository : CrudRepository<SessionEntity, String>
