package piperkt.services.friendships.application

import piperkt.services.friendships.application.api.FriendshipsApi
import piperkt.services.friendships.application.api.command.FriendshipCommand
import piperkt.services.friendships.application.api.query.FriendshipQuery

class FriendshipService(
    private val friendshipAggregateRepository: FriendshipAggregateRepository,
) : FriendshipsApi {
    override fun createFriendship(request: FriendshipCommand.CreateFriendship.Request) {
        println("TODO: Implement createFriendship")
    }

    override fun sendFriendshipRequest(request: FriendshipCommand.SendFriendshipRequest.Request) {
        TODO("Not yet implemented")
    }

    override fun acceptFriendshipRequest(
        request: FriendshipCommand.AcceptFriendshipRequest.Request
    ) {
        TODO("Not yet implemented")
    }

    override fun declineFriendshipRequest(
        request: FriendshipCommand.DeclineFriendshipRequest.Request
    ) {
        TODO("Not yet implemented")
    }

    override fun sendMessage(request: FriendshipCommand.SendMessage.Request) {
        TODO("Not yet implemented")
    }

    override fun getMessages(
        request: FriendshipQuery.GetMessages.Request
    ): FriendshipQuery.GetMessages.Response {
        TODO("Not yet implemented")
    }

    override fun getFriendshipRequests(
        request: FriendshipQuery.GetFriendshipRequests.Request
    ): FriendshipQuery.GetFriendshipRequests.Response {
        TODO("Not yet implemented")
    }

    override fun getFriendships(
        request: FriendshipQuery.GetFriendships.Request
    ): FriendshipQuery.GetFriendships.Response {
        TODO("Not yet implemented")
    }
}
