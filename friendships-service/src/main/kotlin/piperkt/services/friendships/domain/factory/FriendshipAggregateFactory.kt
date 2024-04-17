package piperkt.services.friendships.domain.factory

import piperkt.services.friendships.domain.FriendshipAggregate
import piperkt.services.friendships.domain.Message

object FriendshipAggregateFactory {

    fun createFriendshipAggregate(
        firstUser: String,
        secondUser: String,
        messages: List<Message> = emptyList()
    ) =
        FriendshipAggregate(
            friendshipRequest =
                FriendshipRequestFactory.createFriendshipRequest(firstUser, secondUser),
            friendship = FriendshipFactory.createFriendship(firstUser, secondUser, messages)
        )
}
