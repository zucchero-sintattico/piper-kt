package piperkt.services.multimedia.application.api.query

import piperkt.services.multimedia.application.dto.SessionDTO

data class GetChannelSessionQuery(val channelId: String)

data class GetChannelSessionResponse(val session: SessionDTO)
