package piperkt.services.servers.infrastructure.persistence.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

@MappedEntity
data class ServerEntity(
    @Id @GeneratedValue val id: String? = null,
    val name: String,
    val description: String,
    val owner: String,
    val users: List<String> = listOf(owner),
    val channels: List<ChannelEntity> = emptyList(),
)

@MongoRepository
interface ServerModelRepository : CrudRepository<ServerEntity, String> {
    fun findByUsersContains(member: String): List<ServerEntity>
}
