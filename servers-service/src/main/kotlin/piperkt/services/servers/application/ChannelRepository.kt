package piperkt.services.servers.application

import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.domain.Channel

interface ChannelRepository {
    fun findByServerId(serverId: ServerId): List<Channel>

    fun save(
        serverId: ServerId,
        channelName: String,
        channelDescription: String,
        channelType: String
    ): Channel

    fun update(serverId: ServerId, channelName: String?, channelDescription: String?): Channel?

    fun delete(serverId: ServerId, channelId: ChannelId): Boolean
}
