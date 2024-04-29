package piperkt.services.friendships.interfaces.web

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import java.security.Principal
import piperkt.services.friendships.application.FriendshipService
import piperkt.services.friendships.application.api.command.FriendshipCommand
import piperkt.services.friendships.application.api.query.FriendshipQuery
import piperkt.services.friendships.interfaces.web.api.FriendshipHttpApi
import piperkt.services.friendships.interfaces.web.api.interactions.FriendshipApi

@Controller("/friends")
class FriendshipHttpController(private val friendshipService: FriendshipService) :
    FriendshipHttpApi {

    @Post("/requests/send/")
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

    @Post("/requests/accept/")
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

    @Post("/requests/decline/")
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

    @Get("/requests/")
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

    @Get("/")
    override fun getFriendships(principal: Principal): FriendshipApi.GetFriendships.Response {
        val response =
            friendshipService
                .getFriendships(
                    FriendshipQuery.GetFriendships.Request(requestFrom = principal.name)
                )
                .getOrThrow()
        return FriendshipApi.GetFriendships.Response(friendships = response.friendships)
    }
}
