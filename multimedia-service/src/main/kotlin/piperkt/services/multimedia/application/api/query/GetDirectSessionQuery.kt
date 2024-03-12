package piperkt.services.multimedia.application.api.query

import piperkt.services.multimedia.application.dto.SessionDTO
import piperkt.services.multimedia.application.dto.UserDTO

data class GetDirectSessionQuery(val users: Set<UserDTO>)

data class GetDirectSessionResponse(val session: SessionDTO)
