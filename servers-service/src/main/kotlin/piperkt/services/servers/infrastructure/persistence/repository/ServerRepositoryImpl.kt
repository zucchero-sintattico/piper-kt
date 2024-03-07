package piperkt.services.servers.infrastructure.persistence.repository

import io.micronaut.context.annotation.Primary
import jakarta.inject.Singleton
import kotlin.jvm.optionals.getOrElse
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.domain.Server
import piperkt.services.servers.domain.ServerRepository
import piperkt.services.servers.domain.factory.ServerFactory
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

    override fun findByUser(memberUsername: String): List<Server> {
        return serverModelRepository.findByUsersContains(memberUsername).map {
            ServerFactory.createServer(it.id.orEmpty(), it.name, it.description, it.owner)
        }
    }

    override fun deleteServer(serverId: ServerId) {
        serverModelRepository.deleteById(serverId.value)
    }

    override fun updateServer(
        serverId: ServerId,
        serverName: String?,
        serverDescription: String?
    ): Server? {
        val server =
            serverModelRepository.findById(serverId.value).getOrElse {
                return null
            }
        return serverModelRepository
            .update(
                ServerEntity(
                    id = serverId.value,
                    name = serverName ?: server.name,
                    description = serverDescription ?: server.description,
                    owner = server.owner,
                )
            )
            .let { ServerFactory.createServer(it.id.orEmpty(), it.name, it.description, it.owner) }
    }

    override fun getServerUsers(serverId: ServerId): List<String> =
        serverModelRepository
            .findById(serverId.value)
            .getOrElse {
                return emptyList()
            }
            .users

    override fun addUserToServer(serverId: ServerId, username: String): Server? {
        val server =
            serverModelRepository.findById(serverId.value).getOrElse {
                return null
            }

        val members = server.users.toMutableList().also { it.add(username) }
        return serverModelRepository
            .update(
                ServerEntity(
                    id = serverId.value,
                    name = server.name,
                    description = server.description,
                    owner = server.owner,
                    users = members
                )
            )
            .let {
                ServerFactory.createServerWithMembers(
                    it.id.orEmpty(),
                    it.name,
                    it.description,
                    it.owner,
                    members
                )
            }
    }

    override fun removeUserFromServer(serverId: ServerId, username: String): Server? {
        val server =
            serverModelRepository.findById(serverId.value).getOrElse {
                return null
            }
        val members = server.users.toMutableList().also { it.remove(username) }
        return serverModelRepository
            .update(
                ServerEntity(
                    id = serverId.value,
                    name = server.name,
                    description = server.description,
                    owner = server.owner,
                    users = members
                )
            )
            .let {
                ServerFactory.createServerWithMembers(
                    it.id.orEmpty(),
                    it.name,
                    it.description,
                    it.owner,
                    members
                )
            }
    }
}
