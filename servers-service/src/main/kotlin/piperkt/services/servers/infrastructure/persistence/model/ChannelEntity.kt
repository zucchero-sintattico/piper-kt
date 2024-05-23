package piperkt.services.servers.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import piperkt.services.servers.domain.Channel
import piperkt.services.servers.domain.factory.ChannelFactory

@MappedEntity
data class ChannelEntity(
    @Id val id: String,
    val name: String,
    val description: String,
    val channelType: String,
    val messages: List<MessageEntity> = emptyList()
) {
    fun toDomain() =
        ChannelFactory.createFromType(
            id = id,
            name = name,
            type = channelType,
            description = description,
            messages = messages.map { it.toDomain() }.toMutableList()
        )

    companion object {
        fun fromDomain(channel: Channel) =
            ChannelEntity(
                id = channel.id.value,
                name = channel.name,
                description = channel.description,
                channelType = channel.type.name,
                messages = channel.messages.map { MessageEntity.fromDomain(it) }
            )
    }
}
