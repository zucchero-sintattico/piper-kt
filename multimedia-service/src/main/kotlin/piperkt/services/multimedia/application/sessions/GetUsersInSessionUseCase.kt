package piperkt.services.multimedia.application.sessions

import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.asSuccess
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.sessions.SessionRepository

class GetUsersInSessionUseCase(private val sessionRepository: SessionRepository) {
    class Query(val sessionId: String)

    class Response(val users: Set<String>)

    sealed class Errors : Exception() {
        data class SessionNotFound(val sessionId: String) : Errors()
    }

    fun handle(query: Query): Result<Response> {
        val session =
            sessionRepository.findById(SessionId(query.sessionId))
                ?: return Errors.SessionNotFound(query.sessionId).asFailure()
        return Response(session.participants.map { it.username.value }.toSet()).asSuccess()
    }
}
