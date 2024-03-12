package piperkt.services.servers.infrastructure.persistence.repository

import jakarta.inject.Singleton
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull
import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.ChannelRepository
import piperkt.services.servers.domain.Channel
import piperkt.services.servers.domain.Message
import piperkt.services.servers.domain.factory.ChannelFactory
import piperkt.services.servers.domain.factory.MessageFactory
import piperkt.services.servers.infrastructure.persistence.model.ChannelEntity
import piperkt.services.servers.infrastructure.persistence.model.ChannelModelRepository
import piperkt.services.servers.infrastructure.persistence.model.MessageEntity
import piperkt.services.servers.infrastructure.persistence.model.MessageModelRepository
import piperkt.services.servers.infrastructure.persistence.model.ServerEntity
import piperkt.services.servers.infrastructure.persistence.model.ServerModelRepository

/**
 * Implementation of [ChannelRepository] using Micronaut Data.
 *
 * @property channelModelRepository The repository for [ChannelEntity].
 * @property serverModelRepository The repository for [ServerEntity]. It uses
 *   [ServerModelRepository] to update the server when a channel is added, updated or deleted. And
 *   it uses [ChannelModelRepository] to perform the operations on the channel collection.
 */
@Singleton
class ChannelRepositoryImpl(
    private val channelModelRepository: ChannelModelRepository,
    private val messageModelRepository: MessageModelRepository,
    private val serverModelRepository: ServerModelRepository
) : ChannelRepository {
    override fun findByServerId(serverId: ServerId): List<Channel> {
        val serverEntity =
            serverModelRepository.findById(serverId.value).getOrElse {
                return emptyList()
            }
        return serverEntity.channels.map {
            ChannelFactory.createFromType(it.id.orEmpty(), it.name, it.description, it.channelType)
        }
    }

    override fun save(
        serverId: ServerId,
        channelName: String,
        channelDescription: String,
        channelType: String
    ): Channel? {
        val serverEntity =
            serverModelRepository.findById(serverId.value).getOrElse {
                return null
            }
        val channelEntity =
            channelModelRepository.save(
                ChannelEntity(null, channelName, channelDescription, channelType)
            )
        val channelListUpdated =
            serverEntity.channels.toMutableList().also { it.add(channelEntity) }
        serverModelRepository.update(
            ServerEntity(
                serverEntity.id,
                serverEntity.name,
                serverEntity.description,
                serverEntity.owner,
                serverEntity.users,
                channelListUpdated
            )
        )
        return ChannelFactory.createFromType(
            channelEntity.id.orEmpty(),
            channelName,
            channelDescription,
            channelType
        )
    }

    override fun updateChannel(
        serverId: ServerId,
        channelId: ChannelId,
        channelName: String?,
        channelDescription: String?
    ): Channel? {
        val serverEntity = serverModelRepository.findById(serverId.value).getOrNull()
        var channelEntity = channelModelRepository.findById(channelId.value).getOrNull()
        if (isServerOrChannelNull(serverEntity, channelEntity)) {
            return null
        }
        // I'm sure that serverEntity and channelEntity are not null
        channelEntity = channelEntity!!
        val updatedChannel =
            channelModelRepository.update(
                ChannelEntity(
                    channelId.value,
                    channelName ?: channelEntity.name,
                    channelDescription ?: channelEntity.description,
                    channelEntity.channelType
                )
            )
        val channelListUpdated =
            serverEntity!!.channels.toMutableList().also {
                it.remove(channelEntity)
                it.add(updatedChannel)
            }
        serverModelRepository.update(
            ServerEntity(
                serverEntity.id,
                serverEntity.name,
                serverEntity.description,
                serverEntity.owner,
                serverEntity.users,
                channelListUpdated
            )
        )
        return ChannelFactory.createFromType(
            updatedChannel.id.orEmpty(),
            updatedChannel.name,
            updatedChannel.description,
            updatedChannel.channelType
        )
    }

    override fun delete(serverId: ServerId, channelId: ChannelId): Boolean {
        val server = serverModelRepository.findById(serverId.value).getOrNull()
        val channel = channelModelRepository.findById(channelId.value).getOrNull()
        if (isServerOrChannelNull(server, channel)) {
            return false
        }
        val channels = server!!.channels.toMutableList().also { it.remove(channel) }
        serverModelRepository.update(
            ServerEntity(
                server.id,
                server.name,
                server.description,
                server.owner,
                server.users,
                channels
            )
        )
        return true
    }

    private fun isServerOrChannelNull(server: ServerEntity?, channel: ChannelEntity?): Boolean {
        return server == null || channel == null
    }

    override fun getMessagesFromServerIdAndChannelId(
        channelId: ChannelId,
        from: Int,
        to: Int
    ): List<Message> {
        val channel = channelModelRepository.findById(channelId.value).getOrNull()
        return channel?.messages?.map {
            MessageFactory.createMessage(it.id.orEmpty(), it.content, it.sender, it.timestamp)
        } ?: emptyList()
    }

    override fun addMessageInChannel(
        serverId: ServerId,
        channelId: ChannelId,
        content: String,
        sender: String
    ): Boolean {
        val server = serverModelRepository.findById(serverId.value).getOrNull()
        val channel = channelModelRepository.findById(channelId.value).getOrNull()
        if (isServerOrChannelNull(server, channel) || !isAMessageChannel(channel)) {
            return false
        }
        val messageEntity =
            messageModelRepository.save(MessageEntity(null, content = content, sender = sender))
        val messages = channel!!.messages.toMutableList().also { it.add(messageEntity) }
        // Update Channel Collection
        val channelUpdated =
            channelModelRepository.update(
                ChannelEntity(
                    channel.id,
                    channel.name,
                    channel.description,
                    channel.channelType,
                    messages
                )
            )
        // Update Server Collection
        serverModelRepository.update(
            ServerEntity(
                server!!.id,
                server.name,
                server.description,
                server.owner,
                server.users,
                server.channels.toMutableList().also {
                    it.remove(channel)
                    it.add(channelUpdated)
                }
            )
        )
        return true
    }

    private fun isAMessageChannel(channel: ChannelEntity?): Boolean {
        return channel?.channelType == "TEXT"
    }
}
