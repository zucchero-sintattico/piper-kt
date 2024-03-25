package piperkt.services.multimedia.infrastructure.persistence.repository

import jakarta.inject.Singleton
import piperkt.services.multimedia.domain.Direct
import piperkt.services.multimedia.domain.DirectId
import piperkt.services.multimedia.domain.DirectRepository
import piperkt.services.multimedia.domain.Session
import piperkt.services.multimedia.domain.SessionId
import piperkt.services.multimedia.infrastructure.persistence.model.DirectEntityRepository

@Singleton
class DirectRepositoryImpl(private val directEntityRepository: DirectEntityRepository) :
    DirectRepository {

    override fun getDirectById(directId: DirectId): Direct? {
        val direct = directEntityRepository.findByUsers(directId.value)
        return direct?.let { Direct(directId, SessionId(it.sessionId)) }
    }

    override fun getSessionInDirect(directId: DirectId): Session {
        TODO("Not yet implemented")
    }
}
