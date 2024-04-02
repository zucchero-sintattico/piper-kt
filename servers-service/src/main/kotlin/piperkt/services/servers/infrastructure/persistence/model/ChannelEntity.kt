package piperkt.services.servers.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity

@MappedEntity
data class ChannelEntity(
    @Id val id: String,
    val name: String,
    val description: String,
    val channelType: String,
    val messages: List<MessageEntity> = emptyList()
)
