package piperkt.services.multimedia.domain.session

import piperkt.services.multimedia.domain.user.UserId

sealed class SessionErrors : Exception() {
    data class SessionNotFound(val sessionId: SessionId) : SessionErrors()

    data class UserNotAllowed(val sessionId: SessionId, val userId: UserId) : SessionErrors()

    data class UserNotParticipant(val sessionId: SessionId, val userId: UserId) : SessionErrors()

    data class UserAlreadyAllowed(val sessionId: SessionId, val userId: UserId) : SessionErrors()

    data class UserAlreadyParticipant(val sessionId: SessionId, val userId: UserId) :
        SessionErrors()
}
