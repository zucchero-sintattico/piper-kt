package piperkt.services.servers.application

import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.domain.Channel
import piperkt.services.servers.domain.Message

interface ChannelRepository {
    // Channels
    fun findChannelByServerId(serverId: ServerId): List<Channel>

    fun findChannelById(channelId: ChannelId): Channel?

    fun createChannelInServer(
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

    fun deleteChannel(serverId: ServerId, channelId: ChannelId): Boolean

    // Messages
    fun getMessagesFromServerIdAndChannelId(
        channelId: ChannelId,
        from: Int,
        limit: Int
    ): List<Message>

    fun addMessageInChannel(
        serverId: ServerId,
        channelId: ChannelId,
        content: String,
        sender: String
    ): Message?
}
