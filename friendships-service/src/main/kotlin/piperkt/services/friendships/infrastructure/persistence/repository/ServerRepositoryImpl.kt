package piperkt.services.friendships.infrastructure.persistence.repository

import io.micronaut.context.annotation.Primary
import jakarta.inject.Singleton
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull
import piperkt.common.id.ServerId
import piperkt.services.friendships.application.ServerRepository
import piperkt.services.friendships.domain.Server
import piperkt.services.friendships.infrastructure.persistence.model.ChannelEntity
import piperkt.services.friendships.infrastructure.persistence.model.ServerEntity
import piperkt.services.friendships.infrastructure.persistence.model.ServerModelRepository
import piperkt.services.friendships.infrastructure.persistence.model.toDomain

@Singleton
@Primary
class ServerRepositoryImpl(private val serverModelRepository: ServerModelRepository) :
    ServerRepository {
    override fun save(entity: Server) {
        serverModelRepository.save(
            ServerEntity(
                id = entity.id.value,
                name = entity.name,
                description = entity.description,
                owner = entity.owner,
                users = entity.users,
            )
        )
    }

    override fun findAll(): List<Server> {
        return serverModelRepository.findAll().map { it.toDomain() }
    }

    override fun deleteById(id: ServerId): Server? {
        return serverModelRepository.findById(id.value).getOrNull()?.toDomain().also {
            serverModelRepository.deleteById(id.value)
        }
    }

    override fun update(server: Server): Server {
        val channelEntities =
            server.channels.map {
                ChannelEntity(
                    id = it.id.value,
                    name = it.name,
                    description = it.description,
                    channelType = it.type.toString()
                )
            }
        return serverModelRepository
            .update(
                ServerEntity(
                    id = server.id.value,
                    name = server.name,
                    description = server.description,
                    owner = server.owner,
                    users = server.users,
                    channels = channelEntities
                )
            )
            .toDomain()
    }

    override fun isUserInServer(serverId: ServerId, username: String): Boolean {
        return serverModelRepository
            .findById(serverId.value)
            .getOrElse {
                return false
            }
            .users
            .contains(username)
    }

    override fun findById(id: ServerId): Server? {
        return serverModelRepository.findById(id.value).getOrNull()?.toDomain()
    }

    override fun findByMember(username: String): List<Server> {
        return serverModelRepository.findByUsersContains(username).map { it.toDomain() }
    }

    // Extension method to create server from serverEntity

}
