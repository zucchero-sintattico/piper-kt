package piperkt.services.multimedia.domain.sessions

import piperkt.services.multimedia.domain.users.User

data class Session(val id: SessionId, val allowedUsers: List<User>, val participants: List<User>)
