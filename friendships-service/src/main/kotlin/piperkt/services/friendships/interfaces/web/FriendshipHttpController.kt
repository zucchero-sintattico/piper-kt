package piperkt.services.friendships.interfaces.web

import io.micronaut.http.annotation.Controller
import java.security.Principal
import piperkt.services.friendships.application.FriendshipService
import piperkt.services.friendships.application.api.command.FriendshipCommand
import piperkt.services.friendships.application.api.query.FriendshipQuery
import piperkt.services.friendships.interfaces.web.api.FriendshipHttpApi
import piperkt.services.friendships.interfaces.web.api.interactions.FriendshipApi
import piperkt.services.friendships.presentation.DirectMessageDTO

@Controller
class FriendshipHttpController(private val friendshipService: FriendshipService) :
    FriendshipHttpApi {

    override fun sendFriendshipRequest(
        request: FriendshipApi.SendFriendshipRequest.Request,
        principal: Principal
    ): FriendshipApi.SendFriendshipRequest.Response {
        friendshipService
            .sendFriendshipRequest(
                FriendshipCommand.SendFriendshipRequest.Request(
                    requestFrom = principal.name,
                    receiver = request.receiver
                )
            )
            .getOrThrow()
        return FriendshipApi.SendFriendshipRequest.Response(response = "Request sent successfully")
    }

    override fun acceptFriendshipRequest(
        request: FriendshipApi.AcceptFriendshipRequest.Request,
        principal: Principal
    ): FriendshipApi.AcceptFriendshipRequest.Response {
        val response =
            friendshipService
                .acceptFriendshipRequest(
                    FriendshipCommand.AcceptFriendshipRequest.Request(
                        requestFrom = principal.name,
                        sender = request.sender
                    )
                )
                .getOrThrow()
        return FriendshipApi.AcceptFriendshipRequest.Response(friendshipId = response.friendshipId)
    }

    override fun declineFriendshipRequest(
        request: FriendshipApi.DeclineFriendshipRequest.Request,
        principal: Principal
    ): FriendshipApi.DeclineFriendshipRequest.Response {
        friendshipService
            .declineFriendshipRequest(
                FriendshipCommand.DeclineFriendshipRequest.Request(
                    requestFrom = principal.name,
                    sender = request.sender
                )
            )
            .getOrThrow()
        return FriendshipApi.DeclineFriendshipRequest.Response(
            response = "Request declined successfully"
        )
    }

    override fun getFriendshipRequests(
        principal: Principal
    ): FriendshipApi.GetFriendshipRequests.Response {
        val response =
            friendshipService
                .getFriendshipRequests(
                    FriendshipQuery.GetFriendshipRequests.Request(requestFrom = principal.name)
                )
                .getOrThrow()
        return FriendshipApi.GetFriendshipRequests.Response(requests = response.requests)
    }

    override fun getFriendships(principal: Principal): FriendshipApi.GetFriendships.Response {
        val response =
            friendshipService
                .getFriendships(
                    FriendshipQuery.GetFriendships.Request(requestFrom = principal.name)
                )
                .getOrThrow()
        return FriendshipApi.GetFriendships.Response(friends = response.friendships)
    }

    override fun getFriendshipMessages(
        friendUsername: String,
        from: Int,
        limit: Int,
        principal: Principal
    ): FriendshipApi.GetFriendshipMessages.Response {
        val response =
            friendshipService
                .getMessages(
                    FriendshipQuery.GetMessages.Request(
                        friend = friendUsername,
                        requestFrom = principal.name,
                        index = from,
                        offset = limit
                    )
                )
                .getOrThrow()
        return FriendshipApi.GetFriendshipMessages.Response(
            messages = response.directMessages.map { DirectMessageDTO.fromDomain(it) }
        )
    }

    override fun sendMessage(
        friendUsername: String,
        request: FriendshipApi.SendMessage.Request,
        principal: Principal
    ): FriendshipApi.SendMessage.Response {
        val response =
            friendshipService
                .sendMessage(
                    FriendshipCommand.SendMessage.Request(
                        requestFrom = principal.name,
                        receiver = friendUsername,
                        content = request.content
                    )
                )
                .getOrThrow()
        return FriendshipApi.SendMessage.Response(response.messageId)
    }
}
