package piperkt.services.multimedia.infrastructure.persistence.repository

import jakarta.inject.Singleton
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.sessions.SessionRepository
import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.UserId
import piperkt.services.multimedia.infrastructure.persistence.model.SessionEntityRepository

@Singleton
class SessionRepositoryImpl(private val sessionEntityRepository: SessionEntityRepository) :
    SessionRepository {
    override fun getUsersInSession(sessionId: SessionId): List<User> {
        val session = sessionEntityRepository.findById(sessionId.value)
        return session.map { s -> s.participants.map { User(UserId(it)) } }.orElse(emptyList())
    }
}
