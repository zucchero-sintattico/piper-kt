package piperkt.services.multimedia.application.usecases

import piperkt.services.multimedia.application.QueryUseCase
import piperkt.services.multimedia.domain.session.SessionErrors
import piperkt.services.multimedia.domain.session.SessionId
import piperkt.services.multimedia.domain.session.SessionRepository
import piperkt.services.multimedia.domain.user.Username

/**
 * Gets the participants in a session.
 *
 * @param sessionRepository the repository to access the session
 * @exception SessionErrors.SessionNotFound the session was not found
 */
open class GetSessionParticipants(private val sessionRepository: SessionRepository) :
    QueryUseCase<GetSessionParticipants.Query, GetSessionParticipants.Response> {
    data class Query(val sessionId: SessionId)

    data class Response(val usersIds: Set<Username>)

    override fun execute(query: Query): Response {
        val session =
            sessionRepository.findById(query.sessionId)
                ?: throw SessionErrors.SessionNotFound(query.sessionId)
        return Response(session.participants().toSet())
    }
}
