package piperkt.services.servers.infrastructure.persistence.model

import io.micronaut.data.annotation.GeneratedValue
import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import io.micronaut.data.mongodb.annotation.MongoRepository
import io.micronaut.data.repository.CrudRepository

@MappedEntity
data class ChannelEntity(
    @Id @GeneratedValue val idChannel: String? = null,
    val name: String,
    val description: String,
    val channelType: String
)

@MongoRepository
interface ChannelModelRepository : CrudRepository<ServerEntity, String> {

    fun findChannelsByIdAndId(serverId: String, channelId: String): List<ChannelEntity>

    fun findChannelsById(serverId: String): List<ChannelEntity>

    // update channel by serverId and channelId
    fun updateChannelsById(channel: ChannelEntity)
    //    fun deleteChannelsByIdAndId(serverId: String, channelId: String)

}
