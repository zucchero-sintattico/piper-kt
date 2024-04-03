package piperkt.services.multimedia.domain.session

import piperkt.services.multimedia.domain.user.Username

sealed class SessionErrors : Exception() {
    data class SessionNotFound(val sessionId: SessionId) : SessionErrors()

    data class UserNotAllowed(val sessionId: SessionId, val username: Username) : SessionErrors()

    data class UserNotParticipant(val sessionId: SessionId, val username: Username) :
        SessionErrors()

    data class UserAlreadyAllowed(val sessionId: SessionId, val username: Username) :
        SessionErrors()

    data class UserAlreadyParticipant(val sessionId: SessionId, val username: Username) :
        SessionErrors()
}
