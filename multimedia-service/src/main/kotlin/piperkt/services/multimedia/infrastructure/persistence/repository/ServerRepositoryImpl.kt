package piperkt.services.multimedia.infrastructure.persistence.repository

import jakarta.inject.Singleton
import piperkt.services.multimedia.domain.server.Server
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.server.ServerRepository
import piperkt.services.multimedia.infrastructure.Utils.asNullable
import piperkt.services.multimedia.infrastructure.persistence.model.ServerEntity
import piperkt.services.multimedia.infrastructure.persistence.model.ServerEntityRepository

@Singleton
class ServerRepositoryImpl(private val serverEntityRepository: ServerEntityRepository) :
    ServerRepository {
    override fun findById(id: ServerId): Server? {
        return serverEntityRepository.findById(id.value).asNullable()?.toDomain()
    }

    override fun save(entity: Server): Server {
        val mapped =
            ServerEntity(id = entity.id.value, participants = entity.members().map { it.value })
        return serverEntityRepository.save(mapped).toDomain()
    }

    override fun deleteById(id: ServerId) {
        serverEntityRepository.deleteById(id.value)
    }
}
