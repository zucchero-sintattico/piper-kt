package piperkt.services.multimedia.infrastructure.persistence.repository

import jakarta.inject.Singleton
import piperkt.services.multimedia.domain.server.Server
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.server.ServerRepository
import piperkt.services.multimedia.infrastructure.Utils.asNullable
import piperkt.services.multimedia.infrastructure.persistence.model.ServerEntityRepository
import piperkt.services.multimedia.presentation.ServerMapper.toDomain
import piperkt.services.multimedia.presentation.ServerMapper.toEntity

/** Repository implementation for [Server] */
@Singleton
class ServerRepositoryImpl(private val serverEntityRepository: ServerEntityRepository) :
    ServerRepository {
    override fun findById(id: ServerId): Server? {
        return serverEntityRepository.findById(id.value).asNullable()?.toDomain()
    }

    override fun save(entity: Server) {
        serverEntityRepository.save(entity.toEntity())
    }

    override fun deleteById(id: ServerId): Server? {
        val server = findById(id)
        serverEntityRepository.deleteById(id.value)
        return server
    }

    override fun update(entity: Server) {
        serverEntityRepository.update(entity.toEntity())
    }

    override fun deleteAll() {
        serverEntityRepository.deleteAll()
    }
}
