package piperkt.services.multimedia.domain.session

import piperkt.services.multimedia.common.AggregateRoot
import piperkt.services.multimedia.domain.user.Username

class Session(
    id: SessionId = SessionId(),
    private var allowedUsers: Set<Username> = emptySet(),
    private var participants: Set<Username> = emptySet(),
) : AggregateRoot<SessionId>(id) {

    fun allowedUsers(): List<Username> {
        return allowedUsers.toList()
    }

    fun participants(): List<Username> {
        return participants.toList()
    }

    @Throws(SessionErrors.UserAlreadyParticipant::class)
    fun addParticipant(participant: Username) {
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
