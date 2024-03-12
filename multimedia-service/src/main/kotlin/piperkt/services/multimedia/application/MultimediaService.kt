package piperkt.services.multimedia.application

import piperkt.services.multimedia.application.api.query.GetChannelSessionQuery
import piperkt.services.multimedia.application.api.query.GetChannelSessionResponse
import piperkt.services.multimedia.application.api.query.GetDirectSessionQuery
import piperkt.services.multimedia.application.api.query.GetDirectSessionResponse
import piperkt.services.multimedia.application.api.query.GetUserInSessionQuery
import piperkt.services.multimedia.application.api.query.GetUserInSessionResponse
import piperkt.services.multimedia.application.dto.SessionDTO
import piperkt.services.multimedia.application.dto.UserDTO
import piperkt.services.multimedia.domain.channels.ChannelId
import piperkt.services.multimedia.domain.channels.ChannelRepository
import piperkt.services.multimedia.domain.directs.DirectId
import piperkt.services.multimedia.domain.directs.DirectRepository
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.sessions.SessionRepository

open class MultimediaService(
    private val directRepository: DirectRepository,
    private val sessionRepository: SessionRepository,
    private val channelRepository: ChannelRepository
) {

    fun getUsersInSession(query: GetUserInSessionQuery): GetUserInSessionResponse {
        val users = sessionRepository.getUsersInSession(SessionId(query.sessionId))
        return GetUserInSessionResponse(users.map { UserDTO.fromUser(it) }.toSet())
    }

    fun getDirectSession(request: GetDirectSessionQuery): GetDirectSessionResponse {
        val session =
            directRepository.getSessionInDirect(DirectId(request.users.map { it.username }.toSet()))
        return GetDirectSessionResponse(SessionDTO.fromSession(session))
    }

    fun getChannelSession(request: GetChannelSessionQuery): GetChannelSessionResponse {
        val session = channelRepository.getSessionInChannel(ChannelId(request.channelId))
        return GetChannelSessionResponse(SessionDTO.fromSession(session))
    }
}
