package piperkt.services.multimedia.application.api.query

import piperkt.services.multimedia.application.dto.SessionDTO

data class GetChannelSessionQuery(val channelId: String) {
    data class Response(val session: SessionDTO)

    sealed class Error {
        data class SessionNotFound(val channelId: String) : Error()
    }
}
