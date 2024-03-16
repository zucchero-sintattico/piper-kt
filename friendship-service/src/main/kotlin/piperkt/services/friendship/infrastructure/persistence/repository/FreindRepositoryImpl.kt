package piperkt.services.friendship.infrastructure.persistence.repository

import io.micronaut.context.annotation.Primary
import jakarta.inject.Singleton
import piperkt.services.friendship.domain.Friend
import piperkt.services.friendship.domain.FriendRepository

@Singleton
@Primary
class FreindRepositoryImpl : FriendRepository {

    override fun sendFriendRequest(sender: Friend, receiver: Friend): Nothing {

        TODO()
    }
}
