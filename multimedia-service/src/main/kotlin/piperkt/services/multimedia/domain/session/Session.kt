package piperkt.services.multimedia.domain.session

import piperkt.common.ddd.AggregateRoot
import piperkt.common.ddd.UUIDEntityId
import piperkt.services.multimedia.domain.user.Username

class SessionId(value: String = newId()) : UUIDEntityId(value)

class Session(
    id: SessionId = SessionId(),
    private var allowedUsers: Set<Username> = emptySet(),
    private var participants: Set<Username> = emptySet(),
) : AggregateRoot<SessionId>(id) {

    fun allowedUsers(): Set<Username> {
        return allowedUsers
    }

    fun participants(): Set<Username> {
        return participants
    }

    fun addParticipant(participant: Username) {
        if (!allowedUsers.contains(participant)) {
            throw SessionErrors.UserNotAllowed(id, participant)
        }
        if (participants.contains(participant)) {
            throw SessionErrors.UserAlreadyParticipant(id, participant)
        }
        participants += participant
    }

    fun removeParticipant(participant: Username) {
        if (!participants.contains(participant)) {
            throw SessionErrors.UserNotParticipant(id, participant)
        }
        participants -= participant
    }

    fun addAllowedUser(user: Username) {
        if (allowedUsers.contains(user)) {
            throw SessionErrors.UserAlreadyAllowed(id, user)
        }
        allowedUsers += user
    }

    fun removeAllowedUser(user: Username) {
        if (!allowedUsers.contains(user)) {
            throw SessionErrors.UserNotAllowed(id, user)
        }
        allowedUsers -= user
    }
}
