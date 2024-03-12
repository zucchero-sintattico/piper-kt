package piperkt.services.servers.infrastructure.persistence.repository

import jakarta.inject.Singleton
import kotlin.jvm.optionals.getOrElse
import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.ChannelRepository
import piperkt.services.servers.domain.Channel
import piperkt.services.servers.domain.factory.ChannelFactory
import piperkt.services.servers.infrastructure.persistence.model.ChannelEntity
import piperkt.services.servers.infrastructure.persistence.model.ChannelModelRepository

@Singleton
class ChannelRepositoryImpl(private val channelModelRepository: ChannelModelRepository) :
    ChannelRepository {
    override fun findByServerId(serverId: ServerId): List<Channel> {
        return channelModelRepository.findChannelsById(serverId.value).map {
            ChannelFactory.createFromType(
                it.idChannel.orEmpty(),
                it.name,
                it.description,
                it.channelType
            )
        }
    }

    override fun save(
        serverId: ServerId,
        channelName: String,
        channelDescription: String,
        channelType: String
    ): Channel? {
        val server =
            channelModelRepository.findById(serverId.value).getOrElse {
                return null
            }
        val channel = ChannelEntity(null, channelName, channelDescription, channelType)
        server.channels.toMutableList().add(channel)
        channelModelRepository.update(server)
        return ChannelFactory.createFromType(
            channel.idChannel.orEmpty(),
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
        val channel =
            channelModelRepository
                .findChannelsByIdAndId(serverId.value, channelId.value)
                .firstOrNull()
        if (channel != null) {
            channelModelRepository.updateChannelsById(
                ChannelEntity(
                    channel.idChannel,
                    channelName ?: channel.name,
                    channelDescription ?: channel.description,
                    channel.channelType
                )
            )
            return ChannelFactory.createFromType(
                channel.idChannel.orEmpty(),
                channelName ?: channel.name,
                channelDescription ?: channel.description,
                channel.channelType
            )
        }
        return null
    }

    override fun delete(serverId: ServerId, channelId: ChannelId): Boolean {
        //        val channel = channelModelRepository.findChannelsByIdAndId(serverId.value,
        // channelId.value).firstOrNull()
        //        if (channel != null) {
        //            channelModelRepository.deleteChannelsByIdAndId(serverId.value,
        // channelId.value)
        //            return true
        //        }
        return false
    }
}
