package piperkt.services.multimedia.application.services

import piperkt.services.multimedia.application.orThrow
import piperkt.services.multimedia.domain.server.Channel
import piperkt.services.multimedia.domain.server.ChannelId
import piperkt.services.multimedia.domain.server.Server
import piperkt.services.multimedia.domain.server.ServerErrors.ServerNotFound
import piperkt.services.multimedia.domain.server.ServerId
import piperkt.services.multimedia.domain.server.ServerRepository
import piperkt.services.multimedia.domain.user.Username

open class ServerService(private val serverRepository: ServerRepository) {

    sealed interface Command {

        data class CreateServer(val members: Set<Username>) : Command

        data class DeleteServer(val serverId: ServerId) : Command

        data class AddServerMember(val serverId: ServerId, val username: Username) : Command

        data class RemoveServerMember(val serverId: ServerId, val username: Username) : Command

        data class AddServerChannel(val serverId: ServerId, val channel: Channel) : Command

        data class RemoveServerChannel(val serverId: ServerId, val channelId: ChannelId) : Command
    }

    fun getServer(serverId: ServerId): Server {
        return serverRepository.findById(serverId).orThrow(ServerNotFound(serverId))
    }

    fun createServer(command: Command.CreateServer): Server {
        val server = Server(members = command.members)
        serverRepository.save(server)
        return server
    }

    fun deleteServer(command: Command.DeleteServer) {
        serverRepository.deleteById(command.serverId).orThrow(ServerNotFound(command.serverId))
    }

    private fun updateServer(id: ServerId, update: Server.() -> Unit) {
        val server = getServer(id)
        server.update()
        serverRepository.save(server)
    }

    fun addServerMember(command: Command.AddServerMember) {
        updateServer(command.serverId) { addMember(command.username) }
    }

    fun removeServerMember(command: Command.RemoveServerMember) {
        updateServer(command.serverId) { removeMember(command.username) }
    }

    fun addServerChannel(command: Command.AddServerChannel) {
        updateServer(command.serverId) { addChannel(command.channel) }
    }

    fun removeServerChannel(command: Command.RemoveServerChannel) {
        updateServer(command.serverId) { removeChannelById(command.channelId) }
    }
}
