package piperkt.services.multimedia.infrastructure.persistence.repository

import jakarta.inject.Singleton
import piperkt.services.multimedia.domain.session.Session
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.infrastructure.Utils.asNullable
import piperkt.services.multimedia.infrastructure.persistence.model.SessionEntity
import piperkt.services.multimedia.infrastructure.persistence.model.SessionEntityRepository

@Singleton
class SessionRepositoryImpl(private val sessionEntityRepository: SessionEntityRepository) :
    SessionRepository {
    override fun findById(id: SessionId): Session? {
        return sessionEntityRepository.findById(id.value).asNullable()?.toDomain()
    }

    override fun save(entity: Session) {
        sessionEntityRepository.save(SessionEntity.fromDomain(entity))
    }

    override fun deleteById(id: SessionId) {
        sessionEntityRepository.deleteById(id.value)
    }
}