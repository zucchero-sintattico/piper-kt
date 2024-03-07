package piperkt.services.servers.application

import piperkt.services.servers.application.api.ServerServiceApi
import piperkt.services.servers.application.exception.ServerNotFoundException
import piperkt.services.servers.application.request.AddMemberToServerRequest
import piperkt.services.servers.application.request.CreateServerRequest
import piperkt.services.servers.application.request.DeleteServerRequest
import piperkt.services.servers.application.request.GetServerFromUserRequest
import piperkt.services.servers.application.request.GetServerMembersRequest
import piperkt.services.servers.application.request.KickUserFromServerRequest
import piperkt.services.servers.application.request.RemoveMemberToServerRequest
import piperkt.services.servers.domain.Server
import piperkt.services.servers.domain.ServerRepository

open class ServerService(
    private val serverRepository: ServerRepository,
    // private val eventPublisher: EventPublisher,
) : ServerServiceApi {
    override fun getServersFromUser(request: GetServerFromUserRequest): List<Server> =
        serverRepository.findByMember(request.userId.value)

    override fun createServer(request: CreateServerRequest): Server {
        return serverRepository.save(request.name, request.description, request.owner)
    }

    override fun deleteServer(request: DeleteServerRequest) {
        return serverRepository.deleteServer(request.serverId.value)
    }

    override fun addMemberToServer(request: AddMemberToServerRequest): Server? {
        serverRepository.addServerMember(request.serverId.value, request.userId.value).let { server
            ->
            // eventPublisher.publishEvent(ServerJoinedEvent(server))
            return server ?: throw ServerNotFoundException()
        }
    }

    override fun removeMemberToServer(request: RemoveMemberToServerRequest): Server? {
        serverRepository.removeServerMember(request.serverId.value, request.userId.value).let {
            server ->
            // eventPublisher.publishEvent(ServerLeftEvent(server))
            return server ?: throw ServerNotFoundException()
        }
    }

    override fun kickUserFromServer(request: KickUserFromServerRequest): Server? {
        serverRepository.removeServerMember(request.serverId.value, request.userId.value).let {
            server ->
            // eventPublisher.publishEvent(UserKickedEvent(server))
            return server ?: throw ServerNotFoundException()
        }
    }

    override fun getServerMembers(request: GetServerMembersRequest): List<String> {
        serverRepository.getServerMembers(request.serverId.value).let { members ->
            if (members.isEmpty()) throw ServerNotFoundException()
            return members
        }
    }
}
