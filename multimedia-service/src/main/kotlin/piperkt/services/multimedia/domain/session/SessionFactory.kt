package piperkt.services.multimedia.domain.session

import piperkt.services.multimedia.domain.user.UserId

class SessionImpl(
    id: SessionId = SessionId(),
    allowedUsersId: Set<UserId> = emptySet(),
    participants: Set<UserId> = emptySet(),
) : Session(id, allowedUsersId, participants)

object SessionFactory {

    fun create(
        id: SessionId = SessionId(),
        allowedUsers: Set<UserId> = emptySet(),
        participants: Set<UserId> = emptySet()
    ): Session {
        return SessionImpl(id, allowedUsers, participants)
    }

    fun fromAllowedUsersIds(allowedUsers: Set<UserId>): Session {
        return SessionImpl(allowedUsersId = allowedUsers)
    }

    fun withParticipants(allowedUsers: Set<UserId>, participants: Set<UserId>): Session {
        return SessionImpl(allowedUsersId = allowedUsers, participants = participants)
    }

    fun empty(): Session {
        return SessionImpl()
    }
}
