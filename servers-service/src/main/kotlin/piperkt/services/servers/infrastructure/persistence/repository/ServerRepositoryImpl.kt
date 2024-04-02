package piperkt.services.servers.infrastructure.persistence.repository

import io.micronaut.context.annotation.Primary
import jakarta.inject.Singleton
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.ServerRepository
import piperkt.services.servers.domain.Server
import piperkt.services.servers.domain.factory.ServerFactory
import piperkt.services.servers.infrastructure.persistence.model.ChannelEntity
import piperkt.services.servers.infrastructure.persistence.model.ServerEntity
import piperkt.services.servers.infrastructure.persistence.model.ServerModelRepository

@Singleton
@Primary
class ServerRepositoryImpl(private val serverModelRepository: ServerModelRepository) :
    ServerRepository {
    override fun save(serverName: String, serverDescription: String, owner: String): Server {
        return serverModelRepository
            .save(
                ServerEntity(
                    name = serverName,
                    description = serverDescription,
                    owner = owner,
                    users = listOf(owner)
                )
            )
            .let { ServerFactory.createServer(it.id.orEmpty(), it.name, it.description, it.owner) }
    }

    override fun findAll(): List<Server> {
        return serverModelRepository.findAll().map {
            ServerFactory.createServer(it.id.orEmpty(), it.name, it.description, it.owner)
        }
    }

    override fun delete(serverId: ServerId) {
        return serverModelRepository.deleteById(serverId.value)
    }

    override fun update(server: Server): Server {
        val channelEntities =
            server.channels.map {
                ChannelEntity(
                    id = it.channelId.value,
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
            .let { ServerFactory.createServer(it.id.orEmpty(), it.name, it.description, it.owner) }
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

    override fun findById(serverId: ServerId): Server? {
        return serverModelRepository.findById(serverId.value).getOrNull()?.let {
            ServerFactory.createServer(it.id.orEmpty(), it.name, it.description, it.owner)
        }
    }

    override fun findByMember(username: String): List<Server> {
        return serverModelRepository.findByUsersContains(username).map {
            ServerFactory.createServer(it.id.orEmpty(), it.name, it.description, it.owner)
        }
    }
}
