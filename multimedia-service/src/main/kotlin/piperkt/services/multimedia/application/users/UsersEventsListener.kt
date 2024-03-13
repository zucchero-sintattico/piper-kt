package piperkt.services.multimedia.application.users

import piperkt.services.multimedia.application.users.events.UserJoinedServer
import piperkt.services.multimedia.application.users.events.UserKickedFromServer
import piperkt.services.multimedia.application.users.events.UserLeftServer
import piperkt.services.multimedia.domain.servers.ServerId
import piperkt.services.multimedia.domain.servers.ServerRepository
import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.toUserId

interface UsersEvents {
    fun onUserJoinedServer(event: UserJoinedServer)

    fun onUserLeftServer(event: UserLeftServer)

    fun onUserKickedFromServer(event: UserKickedFromServer)
}

open class UsersEventsListener(private val serverRepository: ServerRepository) : UsersEvents {

    override fun onUserJoinedServer(event: UserJoinedServer) {
        val server = serverRepository.findById(ServerId(event.serverId))
        val updatedServer = server?.addUser(User(event.userId.toUserId()))
        updatedServer?.let { serverRepository.save(it) }
    }

    override fun onUserLeftServer(event: UserLeftServer) {
        val server = serverRepository.findById(ServerId(event.serverId))
        val updatedServer = server?.removeUser(User(event.userId.toUserId()))
        updatedServer?.let { serverRepository.save(it) }
    }

    override fun onUserKickedFromServer(event: UserKickedFromServer) {
        return this.onUserLeftServer(UserLeftServer(event.userId, event.serverId))
    }
}
