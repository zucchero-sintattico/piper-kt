package piperkt.services.multimedia.infrastructure.persistence.repository

import jakarta.inject.Singleton
import piperkt.services.multimedia.domain.direct.Direct
import piperkt.services.multimedia.domain.direct.DirectId
import piperkt.services.multimedia.domain.direct.DirectRepository
import piperkt.services.multimedia.domain.user.Username
import piperkt.services.multimedia.infrastructure.Utils.asNullable
import piperkt.services.multimedia.infrastructure.persistence.model.DirectEntity
import piperkt.services.multimedia.infrastructure.persistence.model.DirectEntityRepository

@Singleton
class DirectRepositoryImpl(private val directEntityRepository: DirectEntityRepository) :
    DirectRepository {
    override fun findByUsers(users: Set<Username>): Direct? {
        return directEntityRepository.findByUsers(users.map { it.value }.toSet())?.toDomain()
    }

    override fun findById(id: DirectId): Direct? {
        return directEntityRepository.findById(id.value).asNullable()?.toDomain()
    }

    override fun save(entity: Direct) {
        directEntityRepository.save(DirectEntity.fromDomain(entity))
    }

    override fun deleteById(id: DirectId): Direct? {
        val direct = findById(id)
        directEntityRepository.deleteById(id.value)
        return direct
    }

    override fun update(entity: Direct) {
        directEntityRepository.update(DirectEntity.fromDomain(entity))
    }

    override fun deleteAll() {
        directEntityRepository.deleteAll()
    }
}
