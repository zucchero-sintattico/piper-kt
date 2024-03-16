package piperkt.services.friendship.infrastructure.persistence.repository

import io.micronaut.context.annotation.Primary
import jakarta.inject.Singleton
import piperkt.services.friendship.domain.FriendRepository

@Singleton
@Primary
class FreindRepositoryImpl : FriendRepository {

    override fun getFriends(username: String): List<String> {
        TODO()
    }

    override fun getFriendRequests(username: String): List<String> {
        TODO()
    }

    override fun sendFriendRequest(username: String, friendUsername: String) {
        TODO()
    }

    override fun acceptFriendRequest(username: String, friendUsername: String) {
        TODO()
    }

    override fun denyFriendRequest(username: String, friendUsername: String) {
        TODO()
    }
}
