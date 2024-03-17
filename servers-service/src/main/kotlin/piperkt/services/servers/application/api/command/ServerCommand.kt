package piperkt.services.servers.application.api.command

import piperkt.services.commons.domain.id.ServerId
import piperkt.services.servers.application.api.ServiceRequest

sealed interface ServerCommand {
    sealed interface CreateServer : ServerCommand {
        data class Request(
            val name: String,
            val description: String,
            val owner: String,
            override val requestFrom: String
        ) : CreateServer, ServiceRequest

        data class Response(val serverId: ServerId) : CreateServer
    }

    sealed interface UpdateServer : ServerCommand {
        data class Request(
            val serverId: ServerId,
            val name: String?,
            val description: String?,
            override val requestFrom: String
        ) : UpdateServer, ServiceRequest

        data class Response(val serverId: ServerId) : UpdateServer
    }

    sealed interface DeleteServer : ServerCommand {
        data class Request(val serverId: ServerId, override val requestFrom: String) :
            DeleteServer, ServiceRequest

        data class Response(val serverId: ServerId) : DeleteServer
    }

    sealed interface AddUserToServer : ServerCommand {
        data class Request(
            val serverId: ServerId,
            val username: String,
            override val requestFrom: String
        ) : AddUserToServer, ServiceRequest

        data class Response(val serverId: ServerId) : AddUserToServer
    }

    sealed interface RemoveUserFromServer : ServerCommand {
        data class Request(
            val serverId: ServerId,
            val username: String,
            override val requestFrom: String
        ) : RemoveUserFromServer, ServiceRequest

        data class Response(val serverId: ServerId) : RemoveUserFromServer
    }

    sealed interface KickUserFromServer : ServerCommand {
        data class Request(
            val serverId: ServerId,
            val username: String,
            override val requestFrom: String
        ) : KickUserFromServer, ServiceRequest

        data class Response(val serverId: ServerId) : KickUserFromServer
    }
}

// data class UpdateServerRequest(
//    val serverId: ServerId,
//    val name: String?,
//    val description: String?,
//    override val requestFrom: String
// ) : ServiceRequest
//
// data class DeleteServerRequest(val serverId: ServerId, override val requestFrom: String) :
//    ServiceRequest
//
// data class AddUserToServerRequest(
//    val serverId: ServerId,
//    val username: String,
//    override val requestFrom: String
// ) : ServiceRequest
//
// data class RemoveUserFromServerRequest(
//    val serverId: ServerId,
//    val username: String,
//    override val requestFrom: String
// ) : ServiceRequest
//
// data class KickUserFromServerRequest(
//    val serverId: ServerId,
//    val username: String,
//    override val requestFrom: String
// ) : ServiceRequest
