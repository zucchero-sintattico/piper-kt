package piperkt.services.multimedia.application.dto

import piperkt.services.multimedia.domain.sessions.Session

data class SessionDTO(
    val id: String,
    val participants: Set<UserDTO>,
) {
    companion object {
        fun fromSession(session: Session): SessionDTO {
            return SessionDTO(
                session.id.value,
                session.participants.map { UserDTO.fromUser(it) }.toSet()
            )
        }
    }
}
