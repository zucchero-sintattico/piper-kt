package piperkt.services.multimedia.infrastructure.persistence.repository

import jakarta.inject.Singleton
import piperkt.services.multimedia.domain.servers.Server
import piperkt.services.multimedia.domain.servers.ServerId
import piperkt.services.multimedia.domain.servers.ServerRepository

@Singleton
class ServerRepositoryImpl : ServerRepository {
    override fun save(server: Server) {
        TODO("Not yet implemented")
    }

    override fun findById(id: ServerId): Server? {
        TODO("Not yet implemented")
    }

    override fun findAll(): List<Server> {
        TODO("Not yet implemented")
    }

    override fun delete(server: Server) {
        TODO("Not yet implemented")
    }

    override fun deleteById(id: ServerId) {
        TODO("Not yet implemented")
    }

    override fun existsById(id: ServerId): Boolean {
        TODO("Not yet implemented")
    }

    override fun count(): Long {
        TODO("Not yet implemented")
    }
}
