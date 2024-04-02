package piperkt.services.multimedia.domain.session

import piperkt.services.multimedia.domain.AggregateRoot
import piperkt.services.multimedia.domain.user.UserId

class Session(
    id: SessionId = SessionId.empty(),
    private var allowedUsers: List<UserId> = emptyList(),
    private var participants: List<UserId> = emptyList()
) : AggregateRoot<SessionId>(id) {

    fun allowedUsers(): List<UserId> {
        return allowedUsers.toList()
    }

    fun participants(): List<UserId> {
        return participants.toList()
    }

    @Throws(SessionErrors.UserAlreadyParticipant::class)
    fun addParticipant(participant: UserId) {
        if (participants.contains(participant)) {
            throw SessionErrors.UserAlreadyParticipant(id, participant)
        }
        participants += participant
    }

    @Throws(SessionErrors.UserNotParticipant::class)
    fun removeParticipant(participant: UserId) {
        if (!participants.contains(participant)) {
            throw SessionErrors.UserNotParticipant(id, participant)
        }
        participants -= participant
    }

    @Throws(SessionErrors.UserAlreadyAllowed::class)
    fun addAllowedUser(user: UserId) {
        if (allowedUsers.contains(user)) {
            throw SessionErrors.UserAlreadyAllowed(id, user)
        }
        allowedUsers += user
    }

    @Throws(SessionErrors.UserNotAllowed::class)
    fun removeAllowedUser(user: UserId) {
        if (!allowedUsers.contains(user)) {
            throw SessionErrors.UserNotAllowed(id, user)
        }
        allowedUsers -= user
    }

    companion object {
        fun findById(id: SessionId, sessionRepository: SessionRepository): Session {
            return sessionRepository.findById(id) ?: throw SessionErrors.SessionNotFound(id)
        }
    }
}
