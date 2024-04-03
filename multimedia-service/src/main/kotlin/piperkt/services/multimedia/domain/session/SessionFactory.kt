package piperkt.services.multimedia.domain.session

import piperkt.common.Factory
import piperkt.services.multimedia.domain.user.Username

object SessionFactory : Factory<Session> {

    fun create(
        id: SessionId = SessionId(),
        allowedUsers: Set<Username> = emptySet(),
        participants: Set<Username> = emptySet()
    ): Session {
        return Session(id, allowedUsers, participants)
    }

    fun fromAllowedUsers(allowedUsers: Set<Username>): Session {
        return Session(allowedUsers = allowedUsers)
    }

    fun fromAllowedParticipants(participants: Set<Username>): Session {
        return Session(allowedUsers = participants, participants = participants)
    }

    fun empty(): Session {
        return Session()
    }
}
