package piperkt.services.servers.interfaces.web.api.query

sealed interface GetServerUsers {
    //    data class HttpRequest(val serverId: String) : GetServerUsers
    data class HttpResponse(val users: List<String>) : GetServerUsers
}
