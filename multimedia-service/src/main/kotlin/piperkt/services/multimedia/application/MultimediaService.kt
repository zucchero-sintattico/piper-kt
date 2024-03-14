package piperkt.services.multimedia.application

import piperkt.services.multimedia.application.api.query.GetChannelSessionQuery
import piperkt.services.multimedia.application.api.query.GetDirectSessionQuery
import piperkt.services.multimedia.application.api.query.GetUserInSessionQuery
import piperkt.services.multimedia.application.dto.SessionDTO
import piperkt.services.multimedia.application.dto.UserDTO
import piperkt.services.multimedia.domain.channels.ChannelId
import piperkt.services.multimedia.domain.channels.ChannelRepository
import piperkt.services.multimedia.domain.directs.DirectId
import piperkt.services.multimedia.domain.directs.DirectRepository
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.sessions.SessionRepository

data class UserNotFound(val username: String) : Exception("User $username not found")

open class MultimediaService(
    private val directRepository: DirectRepository,
    private val sessionRepository: SessionRepository,
    private val channelRepository: ChannelRepository
) : MultimediaApi {

    override fun getUsersInSession(
        query: GetUserInSessionQuery
    ): Result<GetUserInSessionQuery.Response, GetUserInSessionQuery.Errors> {
        val users = sessionRepository.getUsersInSession(SessionId(query.sessionId))
        if (users.isEmpty()) {
            return GetUserInSessionQuery.Errors.SessionNotFound(query.sessionId).asError()
        }
        return GetUserInSessionQuery.Response(users.map { UserDTO.fromUser(it) }.toSet())
            .asSuccess()
    }

    override fun getDirectSession(
        query: GetDirectSessionQuery
    ): Result<GetDirectSessionQuery.Response, GetDirectSessionQuery.Error> {
        val direct =
            directRepository.getSessionInDirect(DirectId(query.users.map { it.username }.toSet()))
        return direct.let { GetDirectSessionQuery.Response(SessionDTO.fromSession(it)).asSuccess() }
    }

    override fun getChannelSession(
        query: GetChannelSessionQuery
    ): Result<GetChannelSessionQuery.Response, GetChannelSessionQuery.Error> {
        val session = channelRepository.getSessionInChannel(ChannelId(query.channelId))
        return session.let {
            GetChannelSessionQuery.Response(SessionDTO.fromSession(it)).asSuccess()
        }
    }
}
