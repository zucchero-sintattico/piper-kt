package piperkt.services.servers.interfaces.web.api.servers

import io.micronaut.serde.annotation.Serdeable
import piperkt.services.servers.interfaces.web.api.dto.ServerDTO

sealed interface ServerApi {

    sealed interface CreateServerApi : ServerApi {

        @Serdeable data class Request(val name: String, val description: String) : CreateServerApi

        @Serdeable data class Response(val serverId: String) : CreateServerApi
    }

    sealed interface UpdateServerApi : ServerApi {
        @Serdeable data class Request(val name: String, val description: String) : UpdateServerApi

        @Serdeable data class Response(val name: String, val description: String) : UpdateServerApi
    }

    sealed interface DeleteServerApi : ServerApi {
        @Serdeable data class Response(val serverId: String) : DeleteServerApi
    }

    sealed interface GetServerUsersApi : ServerApi {

        // Don't need a body for this request
        @Serdeable data class Response(val users: List<String>) : GetServerUsersApi
    }

    sealed interface GetServersFromUserApi : ServerApi {

        // Don't need a body for this request
        @Serdeable data class Response(val servers: List<ServerDTO>) : GetServersFromUserApi
    }
}
