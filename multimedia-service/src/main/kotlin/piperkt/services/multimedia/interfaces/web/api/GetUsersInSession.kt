package piperkt.services.multimedia.interfaces.web.api

import io.micronaut.serde.annotation.Serdeable
import piperkt.services.multimedia.application.api.query.GetUserInSessionQuery

@Serdeable
data class GetUsersInSessionRequest(
    val sessionId: String,
) {
    fun toQuery() = GetUserInSessionQuery(sessionId)
}

@Serdeable
data class GetUsersInSessionResponse(
    val users: Set<String>,
)
