package piperkt.services.multimedia.domain.session

import piperkt.common.AggregateRoot
import piperkt.services.multimedia.domain.user.Username

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

    @Throws(SessionErrors.UserAlreadyParticipant::class)
    fun addParticipant(participant: Username) {
        if (!allowedUsers.contains(participant)) {
            throw SessionErrors.UserNotAllowed(id, participant)
        }
        if (participants.contains(participant)) {
            throw SessionErrors.UserAlreadyParticipant(id, participant)
        }
        participants += participant
    }

    @Throws(SessionErrors.UserNotParticipant::class)
    fun removeParticipant(participant: Username) {
        if (!participants.contains(participant)) {
            throw SessionErrors.UserNotParticipant(id, participant)
        }
        participants -= participant
    }

    @Throws(SessionErrors.UserAlreadyAllowed::class)
    fun addAllowedUser(user: Username) {
        if (allowedUsers.contains(user)) {
            throw SessionErrors.UserAlreadyAllowed(id, user)
        }
        allowedUsers += user
    }

    @Throws(SessionErrors.UserNotAllowed::class)
    fun removeAllowedUser(user: Username) {
        if (!allowedUsers.contains(user)) {
            throw SessionErrors.UserNotAllowed(id, user)
        }
        allowedUsers -= user
    }
}
