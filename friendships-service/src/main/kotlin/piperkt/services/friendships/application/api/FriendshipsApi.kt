package piperkt.services.friendships.application.api

import piperkt.services.friendships.application.api.command.FriendshipCommand
import piperkt.services.friendships.application.api.query.FriendshipQuery

interface FriendshipsApi {

    fun createFriendship(request: FriendshipCommand.CreateFriendship.Request): Result<Unit>

    fun sendFriendshipRequest(
        request: FriendshipCommand.SendFriendshipRequest.Request
    ): Result<Unit>

    fun acceptFriendshipRequest(
        request: FriendshipCommand.AcceptFriendshipRequest.Request
    ): Result<Unit>

    fun declineFriendshipRequest(
        request: FriendshipCommand.DeclineFriendshipRequest.Request
    ): Result<Unit>

    fun sendMessage(request: FriendshipCommand.SendMessage.Request): Result<Unit>

    fun getMessages(
        request: FriendshipQuery.GetMessages.Request
    ): Result<FriendshipQuery.GetMessages.Response>

    fun getFriendshipRequests(
        request: FriendshipQuery.GetFriendshipRequests.Request
    ): Result<FriendshipQuery.GetFriendshipRequests.Response>

    fun getFriendships(
        request: FriendshipQuery.GetFriendships.Request
    ): Result<FriendshipQuery.GetFriendships.Response>
}
