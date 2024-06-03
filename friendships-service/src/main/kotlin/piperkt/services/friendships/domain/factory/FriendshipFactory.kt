package piperkt.services.friendships.domain.factory

import piperkt.common.ddd.Factory
import piperkt.services.friendships.domain.DirectMessage
import piperkt.services.friendships.domain.Friendship

object FriendshipFactory : Factory<Friendship> {

    fun createFriendship(
        firstUser: String,
        secondUser: String,
        directMessages: List<DirectMessage> = emptyList()
    ) =
        Friendship(
            users = setOf(firstUser, secondUser),
            directMessages = directMessages.toMutableList()
        )
}
