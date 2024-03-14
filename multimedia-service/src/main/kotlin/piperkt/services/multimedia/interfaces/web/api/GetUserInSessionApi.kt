package piperkt.services.multimedia.interfaces.web.api

import io.micronaut.http.HttpResponse
import io.micronaut.serde.annotation.Serdeable
import piperkt.services.multimedia.application.api.query.GetUserInSessionQuery

object GetUserInSessionApi {

    const val PATH = "/sessions/{sessionId}/users"

    @Serdeable
    data class Request(val sessionId: String) {
        fun toQuery() = GetUserInSessionQuery(sessionId)
    }

    @Serdeable
    sealed class Response {
        @Serdeable data class Success(val users: Set<String>) : Response()

        @Serdeable data object NotFound : Response()

        companion object {

            fun fromSuccess(result: GetUserInSessionQuery.Response): HttpResponse<Response> =
                HttpResponse.ok(Success(result.users.map { it.username }.toSet()))

            fun fromError(error: GetUserInSessionQuery.Errors): HttpResponse<Response> =
                when (error) {
                    is GetUserInSessionQuery.Errors.SessionNotFound ->
                        HttpResponse.notFound(NotFound)
                    is GetUserInSessionQuery.Errors.UserNotAuthorized -> HttpResponse.unauthorized()
                }
        }
    }
}
