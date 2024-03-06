package piperkt.services.servers.infrastructure.persistence.repository

import io.micronaut.context.annotation.Primary
import jakarta.inject.Singleton
import kotlin.jvm.optionals.getOrElse
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
                    members = listOf(owner)
                )
            )
            .let { ServerFactory.createServer(it.id.orEmpty(), it.name, it.description, it.owner) }
    }

    override fun findByMember(memberUsername: String): List<Server> {
        return serverModelRepository.findByMembersContains(memberUsername).map {
            ServerFactory.createServer(it.id.orEmpty(), it.name, it.description, it.owner)
        }
    }

    override fun deleteServer(serverId: String) {
        serverModelRepository.deleteById(serverId)
    }

    override fun updateServer(
        serverId: String,
        serverName: String?,
        serverDescription: String?
    ): Server? {
        val server =
            serverModelRepository.findById(serverId).getOrElse {
                return null
            }
        return serverModelRepository
            .update(
                ServerEntity(
                    id = serverId,
                    name = serverName ?: server.name,
                    description = serverDescription ?: server.description,
                    owner = server.owner,
                )
            )
            .let { ServerFactory.createServer(it.id.orEmpty(), it.name, it.description, it.owner) }
    }

    override fun getServerMembers(serverId: String): List<String> =
        serverModelRepository
            .findById(serverId)
            .getOrElse {
                return emptyList()
            }
            .members

    override fun addServerMember(serverId: String, member: String): Server? {
        val server =
            serverModelRepository.findById(serverId).getOrElse {
                return null
            }

        val members = server.members.toMutableList().also { it.add(member) }
        return serverModelRepository
            .update(
                ServerEntity(
                    id = serverId,
                    name = server.name,
                    description = server.description,
                    owner = server.owner,
                    members = members
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

    override fun removeServerMember(serverId: String, member: String): Server? {
        val server =
            serverModelRepository.findById(serverId).getOrElse {
                return null
            }
        val members = server.members.toMutableList().also { it.remove(member) }
        return serverModelRepository
            .update(
                ServerEntity(
                    id = serverId,
                    name = server.name,
                    description = server.description,
                    owner = server.owner,
                    members = members
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
