package piperkt.services.multimedia.infrastructure.persistence.repository

import io.micronaut.context.annotation.Primary
import jakarta.inject.Singleton
import piperkt.services.multimedia.domain.session.Session
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.infrastructure.Utils.asNullable
import piperkt.services.multimedia.infrastructure.persistence.model.SessionEntity
import piperkt.services.multimedia.infrastructure.persistence.model.SessionEntityRepository

@Primary
@Singleton
class SessionRepositoryImpl(private val sessionEntityRepository: SessionEntityRepository) :
    SessionRepository {
    override fun findById(id: SessionId): Session? {
        return sessionEntityRepository.findById(id.value).asNullable()?.toDomain()
    }

    override fun save(entity: Session): Session {
        val mapped =
            SessionEntity(
                id = entity.id.value,
                allowedUsers = entity.allowedUsers().map { it.value },
                participants = entity.participants().map { it.value }
            )
        return sessionEntityRepository.save(mapped).toDomain()
    }

    override fun deleteById(id: SessionId) {
        sessionEntityRepository.deleteById(id.value)
    }
}
