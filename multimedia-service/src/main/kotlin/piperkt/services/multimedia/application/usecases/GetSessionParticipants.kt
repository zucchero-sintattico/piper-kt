package piperkt.services.multimedia.application.usecases

import piperkt.services.multimedia.application.QueryUseCase
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.asSuccess
import piperkt.services.multimedia.application.usecases.GetSessionParticipants.Errors.SessionNotFound
import piperkt.services.multimedia.domain.SessionId
import piperkt.services.multimedia.domain.SessionRepository

open class GetSessionParticipants(private val sessionRepository: SessionRepository) :
    QueryUseCase<GetSessionParticipants.Query, GetSessionParticipants.Response> {
    data class Query(val sessionId: String)

    data class Response(val users: Set<String>)

    sealed class Errors : Exception() {
        data class SessionNotFound(val sessionId: String) : Errors()
    }

    override fun invoke(query: Query): Result<Response> {
        val session =
            sessionRepository.findById(SessionId(query.sessionId))
                ?: return SessionNotFound(query.sessionId).asFailure()
        return Response(session.participants.map { it.username.value }.toSet()).asSuccess()
    }
}
