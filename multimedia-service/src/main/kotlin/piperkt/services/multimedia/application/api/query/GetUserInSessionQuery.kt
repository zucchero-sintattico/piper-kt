package piperkt.services.multimedia.application.api.query

import piperkt.services.multimedia.application.dto.UserDTO

data class GetUserInSessionQuery(val sessionId: String) {
    data class Response(val users: Set<UserDTO>)

    sealed class Errors {
        data class SessionNotFound(val sessionId: String) : Errors()

        data class UserNotAuthorized(val sessionId: String, val username: String) : Errors()
    }
}
