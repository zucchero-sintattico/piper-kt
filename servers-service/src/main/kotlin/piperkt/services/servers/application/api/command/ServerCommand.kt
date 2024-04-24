package piperkt.services.servers.application.api.command

import piperkt.common.id.ServerId
import piperkt.services.servers.application.api.ServiceRequest

sealed interface ServerCommand {
    sealed interface CreateServer : ServerCommand {
        data class Request(
            val name: String,
            val description: String,
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

        data class Response(val name: String, val description: String) : UpdateServer
    }

    sealed interface DeleteServer : ServerCommand {
        data class Request(val serverId: ServerId, override val requestFrom: String) :
            DeleteServer, ServiceRequest

        data object Response : DeleteServer
    }

    sealed interface AddUserToServer : ServerCommand {
        data class Request(
            val serverId: ServerId,
            val username: String,
            override val requestFrom: String
        ) : AddUserToServer, ServiceRequest

        data object Response : AddUserToServer
    }

    sealed interface RemoveUserFromServer : ServerCommand {
        data class Request(
            val serverId: ServerId,
            val username: String,
            override val requestFrom: String
        ) : RemoveUserFromServer, ServiceRequest

        data object Response : RemoveUserFromServer
    }

    sealed interface KickUserFromServer : ServerCommand {
        data class Request(
            val serverId: ServerId,
            val username: String,
            override val requestFrom: String
        ) : KickUserFromServer, ServiceRequest

        data object Response : KickUserFromServer
    }
}
