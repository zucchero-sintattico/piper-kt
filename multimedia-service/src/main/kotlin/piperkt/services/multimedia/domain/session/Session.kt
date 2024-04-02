package piperkt.services.multimedia.domain.session

import piperkt.services.multimedia.domain.AggregateRoot
import piperkt.services.multimedia.domain.user.UserId

abstract class Session(
    id: SessionId,
    private var allowedUsersId: Set<UserId> = emptySet(),
    private var participants: Set<UserId> = emptySet(),
) : AggregateRoot<SessionId>(id) {

    fun allowedUsersId(): List<UserId> {
        return allowedUsersId.toList()
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
        if (allowedUsersId.contains(user)) {
            throw SessionErrors.UserAlreadyAllowed(id, user)
        }
        allowedUsersId += user
    }

    @Throws(SessionErrors.UserNotAllowed::class)
    fun removeAllowedUser(user: UserId) {
        if (!allowedUsersId.contains(user)) {
            throw SessionErrors.UserNotAllowed(id, user)
        }
        allowedUsersId -= user
    }
}
