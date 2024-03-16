package piperkt.services.multimedia.infrastructure.persistence.repository

import jakarta.inject.Singleton
import kotlin.jvm.optionals.getOrNull
import piperkt.services.multimedia.domain.sessions.Session
import piperkt.services.multimedia.domain.sessions.SessionId
import piperkt.services.multimedia.domain.sessions.SessionRepository
import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.UserId
import piperkt.services.multimedia.infrastructure.persistence.model.SessionEntityRepository

@Singleton
class SessionRepositoryImpl(private val sessionEntityRepository: SessionEntityRepository) :
    SessionRepository {

    override fun findById(id: SessionId): Session? {
        val session = sessionEntityRepository.findById(id.value)
        return session.map { it.toDomain() }.getOrNull()
    }

    override fun getUsersInSession(sessionId: SessionId): List<User> {
        val session = sessionEntityRepository.findById(sessionId.value)
        return session.map { s -> s.participants.map { User(UserId(it)) } }.orElse(emptyList())
    }
}
