package piperkt.services.friendship.infrastructure.persistence.repository

import io.micronaut.context.annotation.Primary
import jakarta.inject.Singleton
import piperkt.services.friendship.domain.Friend
import piperkt.services.friendship.domain.FriendRepository

@Singleton
@Primary
class FriendRepositoryImpl : FriendRepository {

    override fun getFriends(username: String): List<Friend> {
        TODO()
    }

    override fun getFriendRequests(username: String): List<Friend> {
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
