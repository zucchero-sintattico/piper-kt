package piperkt.services.multimedia.domain.session

import piperkt.services.multimedia.domain.user.UserId

object SessionOps {
    fun Session.isAllowed(user: UserId): Boolean {
        return allowedUsersId().contains(user)
    }

    fun Session.isParticipant(user: UserId): Boolean {
        return participants().contains(user)
    }
}
