package piperkt.services.multimedia.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import piperkt.services.multimedia.domain.direct.Direct
import piperkt.services.multimedia.domain.direct.DirectId
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.user.Username

@MappedEntity
data class DirectEntity(@Id val users: Set<String>, val sessionId: String) {
    fun toDomain() =
        Direct(id = DirectId(users.map { Username(it) }.toSet()), sessionId = SessionId(sessionId))

    companion object {
        fun fromDomain(direct: Direct) =
            DirectEntity(
                users = direct.id.value.map { it.value }.toSet(),
                sessionId = direct.sessionId.value
            )
    }
}

@MongoRepository interface DirectEntityRepository : CrudRepository<DirectEntity, Set<String>>
