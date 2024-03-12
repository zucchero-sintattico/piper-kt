package piperkt.services.multimedia.application.api.query

import piperkt.services.multimedia.application.dto.UserDTO

data class GetUserInSessionQuery(val sessionId: String)

data class GetUserInSessionResponse(val users: Set<UserDTO>)
