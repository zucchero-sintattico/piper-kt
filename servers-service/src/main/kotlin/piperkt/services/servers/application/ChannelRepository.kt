package piperkt.services.servers.application

import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.domain.Channel
import piperkt.services.servers.domain.Message

interface ChannelRepository {
    // Channels
    fun findByServerId(serverId: ServerId): List<Channel>

    fun save(
        serverId: ServerId,
        channelName: String,
        channelDescription: String,
        channelType: String
    ): Channel?

    fun updateChannel(
        serverId: ServerId,
        channelId: ChannelId,
        channelName: String?,
        channelDescription: String?
    ): Channel?

    fun delete(serverId: ServerId, channelId: ChannelId): Boolean

    // Messages
    fun getMessagesFromServerIdAndChannelId(channelId: ChannelId, from: Int, to: Int): List<Message>

    fun addMessageInChannel(
        serverId: ServerId,
        channelId: ChannelId,
        content: String,
        sender: String
    ): Boolean
}
