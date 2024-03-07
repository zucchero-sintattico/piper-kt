package piperkt.services.servers.domain

import piperkt.services.commons.domain.id.ServerId

interface ChannelRepository {
    fun findById(id: String): Channel?

    fun save(serverId: ServerId, channel: Channel): Channel

    fun delete(serverId: ServerId, channel: Channel)

    fun findByServerId(serverId: ServerId): List<Channel>

    fun findByUserId(userId: String): List<Channel>
}
