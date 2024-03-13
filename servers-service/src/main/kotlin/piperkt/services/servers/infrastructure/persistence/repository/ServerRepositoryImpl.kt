package piperkt.services.servers.infrastructure.persistence.repository

import io.micronaut.context.annotation.Primary
import jakarta.inject.Singleton
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.ServerRepository
import piperkt.services.servers.domain.Server
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

    override fun getServersFromUser(memberUsername: String): List<Server> {
        return serverModelRepository.findByUsersContains(memberUsername).map {
            ServerFactory.createServer(it.id.orEmpty(), it.name, it.description, it.owner)
        }
    }

    override fun deleteServer(serverId: ServerId): Boolean {
        serverModelRepository.findById(serverId.value).getOrElse {
            return false
        }
        serverModelRepository.deleteById(serverId.value)
        return true
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
}
