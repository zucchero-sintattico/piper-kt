package piperkt.services.multimedia.infrastructure.persistence.repository

import jakarta.inject.Singleton
import piperkt.services.multimedia.domain.direct.Direct
import piperkt.services.multimedia.domain.direct.DirectId
import piperkt.services.multimedia.domain.direct.DirectRepository
import piperkt.services.multimedia.domain.user.Username
import piperkt.services.multimedia.infrastructure.Utils.asNullable
import piperkt.services.multimedia.infrastructure.persistence.model.DirectEntityRepository
import piperkt.services.multimedia.presentation.DirectMapper.toDomain
import piperkt.services.multimedia.presentation.DirectMapper.toEntity

/** Repository implementation for [Direct] */
@Singleton
class DirectRepositoryImpl(private val directEntityRepository: DirectEntityRepository) :
    DirectRepository {
    override fun findByUsers(users: Set<Username>): Direct? {
        val first = directEntityRepository.findByUsers(users.map { it.value }.toSet())?.toDomain()
        val second =
            directEntityRepository
                .findByUsers(users.map { it.value }.reversed().toSet())
                ?.toDomain()
        return first ?: second
    }

    override fun findById(id: DirectId): Direct? {
        return directEntityRepository.findById(id.value).asNullable()?.toDomain()
    }

    override fun save(entity: Direct) {
        directEntityRepository.save(entity.toEntity())
    }

    override fun deleteById(id: DirectId): Direct? {
        val direct = findById(id)
        directEntityRepository.deleteById(id.value)
        return direct
    }

    override fun update(entity: Direct) {
        directEntityRepository.update(entity.toEntity())
    }

    override fun deleteAll() {
        directEntityRepository.deleteAll()
    }
}
