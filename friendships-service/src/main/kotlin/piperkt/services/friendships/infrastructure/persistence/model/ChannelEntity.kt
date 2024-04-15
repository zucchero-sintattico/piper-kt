package piperkt.services.friendships.infrastructure.persistence.model

import io.micronaut.data.annotation.Id
import io.micronaut.data.annotation.MappedEntity
import piperkt.services.friendships.domain.Channel
import piperkt.services.friendships.domain.factory.ChannelFactory

@MappedEntity
data class ChannelEntity(
    @Id val id: String,
    val name: String,
    val description: String,
    val channelType: String,
    val messages: List<MessageEntity> = emptyList()
) {
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

fun ChannelEntity.toDomain() =
    ChannelFactory.createFromType(
        id = id,
        name = name,
        type = channelType,
        description = description,
        messages = messages.map { it.toDomain() }.toMutableList()
    )
