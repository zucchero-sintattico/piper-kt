package piperkt.services.multimedia.application

import piperkt.services.multimedia.domain.sessions.SessionId

interface GetUsersInSessionUseCase : RequireSessionRepository {
    class Query(val sessionId: String)

    class Response(val users: Set<String>)

    sealed class Errors : Exception() {
        data class SessionNotFound(val sessionId: String) : Errors()
    }

    fun getUsersInSession(query: Query): Result<Response> {
        val session =
            sessionRepository.findById(SessionId(query.sessionId))
                ?: return Errors.SessionNotFound(query.sessionId).asFailure()
        return Response(session.participants.map { it.username.value }.toSet()).asSuccess()
    }
}
