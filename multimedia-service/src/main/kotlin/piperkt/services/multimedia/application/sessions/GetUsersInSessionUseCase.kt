package piperkt.services.multimedia.application.sessions

import piperkt.services.multimedia.application.UseCase
import piperkt.services.multimedia.application.asFailure
import piperkt.services.multimedia.application.asSuccess
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.sessions.SessionRepository

open class GetUsersInSessionUseCase(private val sessionRepository: SessionRepository) :
    UseCase<GetUsersInSessionUseCase.Query, GetUsersInSessionUseCase.Response> {
    data class Query(val sessionId: String)

    data class Response(val users: Set<String>)

    sealed class Errors : Exception() {
        data class SessionNotFound(val sessionId: String) : Errors()
    }

    override fun handle(query: Query): Result<Response> {
        val session =
            sessionRepository.findById(SessionId(query.sessionId))
                ?: return Errors.SessionNotFound(query.sessionId).asFailure()
        return Response(session.participants.map { it.username.value }.toSet()).asSuccess()
    }
}
