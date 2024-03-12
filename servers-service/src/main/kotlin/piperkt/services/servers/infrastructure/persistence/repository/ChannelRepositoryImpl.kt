package piperkt.services.servers.infrastructure.persistence.repository

import jakarta.inject.Singleton
import kotlin.jvm.optionals.getOrElse
import kotlin.jvm.optionals.getOrNull
import piperkt.services.commons.domain.id.ChannelId
import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.ChannelRepository
import piperkt.services.servers.domain.Channel
import piperkt.services.servers.domain.factory.ChannelFactory
import piperkt.services.servers.infrastructure.persistence.model.ChannelEntity
import piperkt.services.servers.infrastructure.persistence.model.ChannelModelRepository
import piperkt.services.servers.infrastructure.persistence.model.ServerEntity
import piperkt.services.servers.infrastructure.persistence.model.ServerModelRepository

@Singleton
class ChannelRepositoryImpl(
    private val channelModelRepository: ChannelModelRepository,
    private val serverModelRepository: ServerModelRepository
) : ChannelRepository {
    override fun findByServerId(serverId: ServerId): List<Channel> {
        val server =
            serverModelRepository.findById(serverId.value).getOrElse {
                return emptyList()
            }
        return server.channels.map {
            ChannelFactory.createFromType(it.id.orEmpty(), it.name, it.description, it.channelType)
        }
    }

    override fun save(
        serverId: ServerId,
        channelName: String,
        channelDescription: String,
        channelType: String
    ): Channel? {
        val server =
            serverModelRepository.findById(serverId.value).getOrElse {
                return null
            }
        val channel =
            channelModelRepository.save(
                ChannelEntity(null, channelName, channelDescription, channelType)
            )
        val channels = server.channels.toMutableList().also { it.add(channel) }
        return serverModelRepository
            .update(
                ServerEntity(
                    server.id,
                    server.name,
                    server.description,
                    server.owner,
                    server.users,
                    channels
                )
            )
            .let {
                ChannelFactory.createFromType(
                    channel.id.orEmpty(),
                    channelName,
                    channelDescription,
                    channelType
                )
            }
    }

    override fun updateChannel(
        serverId: ServerId,
        channelId: ChannelId,
        channelName: String?,
        channelDescription: String?
    ): Channel? {
        val server = serverModelRepository.findById(serverId.value).getOrNull()
        val channel = channelModelRepository.findById(channelId.value).getOrNull()
        if (isServerOrChannelNull(server, channel)) {
            return null
        }
        val updatedChannel =
            channelModelRepository.update(
                ChannelEntity(
                    channelId.value,
                    channelName ?: channel!!.name,
                    channelDescription ?: channel!!.description,
                    channel!!.channelType
                )
            )
        val channelListUpdated =
            server!!.channels.toMutableList().also {
                it.remove(channel)
                it.add(updatedChannel)
            }
        return serverModelRepository
            .update(
                ServerEntity(
                    server.id,
                    server.name,
                    server.description,
                    server.owner,
                    server.users,
                    channelListUpdated
                )
            )
            .let {
                ChannelFactory.createFromType(
                    updatedChannel.id.orEmpty(),
                    updatedChannel.name,
                    updatedChannel.description,
                    updatedChannel.channelType
                )
            }
    }

    override fun delete(serverId: ServerId, channelId: ChannelId): Boolean {
        val server = serverModelRepository.findById(serverId.value).getOrNull()
        val channel = channelModelRepository.findById(channelId.value).getOrNull()
        if (isServerOrChannelNull(server, channel)) {
            return false
        }
        val channels = server!!.channels.toMutableList().also { it.remove(channel) }
        return serverModelRepository
            .update(
                ServerEntity(
                    server.id,
                    server.name,
                    server.description,
                    server.owner,
                    server.users,
                    channels
                )
            )
            .let { true }
    }

    private fun isServerOrChannelNull(server: ServerEntity?, channel: ChannelEntity?): Boolean {
        return server == null || channel == null
    }
}
