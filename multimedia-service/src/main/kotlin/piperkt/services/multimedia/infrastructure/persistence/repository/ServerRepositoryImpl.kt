package piperkt.services.multimedia.infrastructure.persistence.repository

import jakarta.inject.Singleton
import piperkt.services.multimedia.domain.Server
import piperkt.services.multimedia.domain.ServerId
import piperkt.services.multimedia.domain.ServerRepository
import piperkt.services.multimedia.domain.User

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

    override fun addUser(id: ServerId, user: User) {
        TODO("Not yet implemented")
    }

    override fun removeUser(id: ServerId, user: User) {
        TODO("Not yet implemented")
    }
}
