package piperkt.services.multimedia.application.sessions.usecases

import piperkt.services.multimedia.application.QueryUseCase
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.asSuccess
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.sessions.SessionRepository

open class GetSessionParticipantsUseCase(private val sessionRepository: SessionRepository) :
    QueryUseCase<GetSessionParticipantsUseCase.Query, GetSessionParticipantsUseCase.Response> {
    data class Query(val sessionId: String)

    data class Response(val users: Set<String>)

    sealed class Errors : Exception() {
        data class SessionNotFound(val sessionId: String) : Errors()
    }

    override fun invoke(query: Query): Result<Response> {
        val session =
            sessionRepository.findById(SessionId(query.sessionId))
                ?: return Errors.SessionNotFound(query.sessionId).asFailure()
        return Response(session.participants.map { it.username.value }.toSet()).asSuccess()
    }
}
