package piperkt.services.friendships.domain.factory

import piperkt.services.friendships.domain.Friendship
import piperkt.services.friendships.domain.Message

object FriendshipFactory {

    fun createFriendship(
        firstUser: String,
        secondUser: String,
        messages: List<Message> = emptyList()
    ) = Friendship(users = setOf(firstUser, secondUser), messages = messages.toMutableList())
}
