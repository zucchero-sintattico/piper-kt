package piperkt.services.friendship.infrastructure.persistence.repository

import jakarta.inject.Singleton
import piperkt.services.friendship.domain.Direct
import piperkt.services.friendship.domain.DirectRepository
import piperkt.services.friendship.domain.Message
import piperkt.services.friendship.infrastructure.persistence.model.DirectModelRepository

@Singleton
class DirectRepositoryImpl(private val directModelRepository: DirectModelRepository) : DirectRepository {
    override fun getDirectMessagesPaginated(
        username: String,
        friendUsername: String,
        from: Int,
        limit: Int
    ): List<Message> {
        TODO("Not yet implemented")
    }

    override fun createDirect(username: String, friendUsername: String) {
        TODO("Not yet implemented")
    }

    override fun sendDirectMessage(username: String, friendUsername: String, message: Message) {
        TODO("Not yet implemented")
    }

}