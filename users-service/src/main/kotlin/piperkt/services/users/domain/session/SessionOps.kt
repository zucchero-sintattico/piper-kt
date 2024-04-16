package piperkt.services.multimedia.domain.session

import piperkt.services.multimedia.domain.user.Username

object SessionOps {
    fun Session.isAllowed(user: Username): Boolean {
        return allowedUsers().contains(user)
    }

    fun Session.isParticipant(user: Username): Boolean {
        return participants().contains(user)
    }
}
