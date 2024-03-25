package piperkt.services.multimedia.infrastructure.persistence.repository

import io.micronaut.context.annotation.Primary
import jakarta.inject.Singleton
import piperkt.services.multimedia.domain.Session
import piperkt.services.multimedia.domain.SessionId
import piperkt.services.multimedia.domain.SessionRepository
import piperkt.services.multimedia.domain.User
import piperkt.services.multimedia.domain.Username
import piperkt.services.multimedia.infrastructure.Utils.asNullable
import piperkt.services.multimedia.infrastructure.persistence.model.SessionEntityRepository

@Primary
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

    override fun deleteSession(sessionId: String) {
        TODO("Not yet implemented")
    }

    override fun addParticipant(sessionId: SessionId, user: User): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeParticipant(sessionId: SessionId, user: User): Boolean {
        TODO("Not yet implemented")
    }

    override fun addAllowedUser(sessionId: SessionId, user: User): Boolean {
        TODO("Not yet implemented")
    }

    override fun removeAllowedUser(sessionId: SessionId, user: User): Boolean {
        TODO("Not yet implemented")
    }

    override fun exists(sessionId: String): Boolean = findById(SessionId(sessionId)) != null
}
