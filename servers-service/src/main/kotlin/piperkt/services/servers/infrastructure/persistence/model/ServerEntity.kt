package piperkt.services.servers.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository
import piperkt.services.servers.domain.Server
import piperkt.services.servers.domain.factory.ServerFactory

@MappedEntity
data class ServerEntity(
    @Id val id: String,
    val name: String,
    val description: String,
    val owner: String,
    val users: List<String> = listOf(owner),
    val channels: List<ChannelEntity> = emptyList(),
) {

    fun toDomain() =
        ServerFactory.createServer(
            id = id,
            name = name,
            owner = owner,
            description = description,
            channels = channels.map { it.toDomain() }.toMutableList(),
            users = users.toMutableList()
        )

    companion object {
        fun fromDomain(server: Server) =
            ServerEntity(
                id = server.id.value,
                name = server.name,
                description = server.description,
                owner = server.owner,
                users = server.users,
                channels = server.channels.map { ChannelEntity.fromDomain(it) }
            )
    }
}

@MongoRepository
interface ServerModelRepository : CrudRepository<ServerEntity, String> {
    fun findByUsersContains(member: String): List<ServerEntity>
}
