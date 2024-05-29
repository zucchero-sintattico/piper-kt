package piperkt.services.multimedia.infrastructure.persistence.repository

import jakarta.inject.Singleton
import piperkt.services.multimedia.domain.session.Session
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.infrastructure.Utils.asNullable
import piperkt.services.multimedia.infrastructure.persistence.model.SessionEntityRepository
import piperkt.services.multimedia.presentation.SessionMapper.toDomain
import piperkt.services.multimedia.presentation.SessionMapper.toEntity

/** Repository implementation for [Session] */
@Singleton
class SessionRepositoryImpl(private val sessionEntityRepository: SessionEntityRepository) :
    SessionRepository {
    override fun findById(id: SessionId): Session? {
        return sessionEntityRepository.findById(id.value).asNullable()?.toDomain()
    }

    override fun save(entity: Session) {
        sessionEntityRepository.save(entity.toEntity())
    }

    override fun deleteById(id: SessionId): Session? {
        val session = findById(id)
        sessionEntityRepository.deleteById(id.value)
        return session
    }

    override fun update(entity: Session) {
        sessionEntityRepository.update(entity.toEntity())
    }

    override fun deleteAll() {
        sessionEntityRepository.deleteAll()
    }
}
