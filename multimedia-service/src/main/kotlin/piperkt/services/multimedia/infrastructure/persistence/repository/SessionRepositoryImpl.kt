package piperkt.services.multimedia.infrastructure.persistence.repository

import jakarta.inject.Singleton
import piperkt.services.multimedia.domain.sessions.Session
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.sessions.SessionRepository
import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.Username
import piperkt.services.multimedia.infrastructure.Utils.asNullable
import piperkt.services.multimedia.infrastructure.persistence.model.SessionEntityRepository

@Singleton
class SessionRepositoryImpl(private val sessionEntityRepository: SessionEntityRepository) :
    SessionRepository {

    override fun findById(id: SessionId): Session? {
        val session = sessionEntityRepository.findById(id.value)
        return session.map { it.toDomain() }.asNullable()
    }

    override fun getSessionParticipants(sessionId: SessionId): List<User> {
        val session = sessionEntityRepository.findById(sessionId.value)
        return session.map { s -> s.participants.map { User(Username(it)) } }.orElse(emptyList())
    }

    override fun createSession(allowedUsers: List<String>): Session {
        TODO("Not yet implemented")
    }

    override fun addParticipant(sessionId: SessionId, username: String): Any {
        TODO("Not yet implemented")
    }

    override fun removeParticipant(sessionId: SessionId, username: String) {
        TODO("Not yet implemented")
    }

    override fun addAllowedUser(sessionId: SessionId, username: String) {
        TODO("Not yet implemented")
    }

    override fun removeAllowedUser(sessionId: SessionId, username: String) {
        TODO("Not yet implemented")
    }

    override fun deleteSession(sessionId: String) {
        TODO("Not yet implemented")
    }

    override fun exists(sessionId: String): Boolean {
        TODO("Not yet implemented")
    }
}
