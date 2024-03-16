package piperkt.services.servers.interfaces.web.api.responses

import io.micronaut.serde.annotation.Serdeable
import piperkt.services.servers.application.api.query.servers.GetServerUsersQueryResponse

/**
 * Basically wrap the response from the application layer into a response that can be serialized
 * into JSON
 */
@Serdeable
data class GetServerUsersHttpResponse(val users: List<String>) {
    companion object {
        fun fromResponse(response: GetServerUsersQueryResponse): GetServerUsersHttpResponse {
            return GetServerUsersHttpResponse(response.users)
        }
    }
}
