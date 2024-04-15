package piperkt.services.friendships.interfaces.web.api.servers

import io.micronaut.serde.annotation.Serdeable

sealed interface GetServerUsersApi {

    //    @Serdeable
    //    data class Request(val serverId: String) : GetServerUsersApi

    @Serdeable data class Response(val users: List<String>) : GetServerUsersApi
}
