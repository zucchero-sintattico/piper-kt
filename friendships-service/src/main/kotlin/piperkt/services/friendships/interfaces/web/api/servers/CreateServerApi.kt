package piperkt.services.friendships.interfaces.web.api.servers

import io.micronaut.serde.annotation.Serdeable

sealed interface CreateServerApi {

    @Serdeable data class Request(val name: String, val description: String) : CreateServerApi

    @Serdeable data class Response(val serverId: String) : CreateServerApi
}
