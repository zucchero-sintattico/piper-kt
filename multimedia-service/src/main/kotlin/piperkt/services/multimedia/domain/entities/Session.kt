package piperkt.services.multimedia.domain.entities

import piperkt.services.multimedia.domain.User
import piperkt.services.multimedia.domain.id.SessionId

interface Session {
    val sessionId: SessionId
    val allowedUsers: List<User>
    val participants: List<User>
}
