package piperkt.services.friendships.application.api

import piperkt.services.friendships.application.api.command.FriendshipCommand
import piperkt.services.friendships.application.api.query.FriendshipQuery

interface FriendshipsApi {

    fun createFriendship(request: FriendshipCommand.CreateFriendship.Request)

    fun sendFrienshipRequest(request: FriendshipCommand.SendFriendshipRequest.Request)

    fun acceptFriendshipRequest(request: FriendshipCommand.AcceptFriendshipRequest.Request)

    fun declineFriendshipRequest(request: FriendshipCommand.DeclineFriendshipRequest.Request)

    fun sendMessage(request: FriendshipCommand.SendMessage.Request)

    fun getMessages(
        request: FriendshipQuery.GetMessages.Request
    ): FriendshipQuery.GetMessages.Response

    fun getFriendshipRequests(
        request: FriendshipQuery.GetFriendshipRequests.Request
    ): FriendshipQuery.GetFriendshipRequests.Response

    fun getFriendships(
        request: FriendshipQuery.GetFriendships.Request
    ): FriendshipQuery.GetFriendships.Response
}
