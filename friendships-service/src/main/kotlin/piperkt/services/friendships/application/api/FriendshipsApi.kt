package piperkt.services.friendships.application.api

import piperkt.services.friendships.application.api.command.FriendshipCommand

interface FriendshipsApi {

    fun createFriendship(request: FriendshipCommand.CreateFriendship.Request)

    fun sendFrienshipRequest(request: FriendshipCommand.SendFriendshipRequest.Request)

    fun acceptFriendshipRequest(request: FriendshipCommand.AcceptFriendshipRequest.Request)

    fun declineFriendshipRequest(request: FriendshipCommand.DeclineFriendshipRequest.Request)

    fun sendMessage(request: FriendshipCommand.SendMessage.Request)

    fun getMessages(
        request: FriendshipCommand.GetMessages.Request
    ): FriendshipCommand.GetMessages.Response

    fun getFriendshipRequests(
        request: FriendshipCommand.GetFriendshipRequests.Request
    ): FriendshipCommand.GetFriendshipRequests.Response

    fun getFriendships(
        request: FriendshipCommand.GetFriendships.Request
    ): FriendshipCommand.GetFriendships.Response
}
