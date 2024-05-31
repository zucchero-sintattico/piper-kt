package piperkt.services.friendships.interfaces.web.api

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.QueryValue
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import java.security.Principal
import piperkt.services.friendships.interfaces.web.api.interactions.FriendshipApi

@Secured(SecurityRule.IS_AUTHENTICATED)
interface FriendshipHttpApi {

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Friendship request sent successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "403", description = "Forbidden"),
    )
    @Post("/friends/requests/send/")
    fun sendFriendshipRequest(
        @Body request: FriendshipApi.SendFriendshipRequest.Request,
        principal: Principal
    ): FriendshipApi.SendFriendshipRequest.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Friendship request accepted successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "404", description = "Friendship request not found"),
    )
    @Post("/friends/requests/accept/")
    fun acceptFriendshipRequest(
        @Body request: FriendshipApi.AcceptFriendshipRequest.Request,
        principal: Principal
    ): FriendshipApi.AcceptFriendshipRequest.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Friendship request declined successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "404", description = "Friendship request not found"),
    )
    @Post("/friends/requests/decline/")
    fun declineFriendshipRequest(
        @Body request: FriendshipApi.DeclineFriendshipRequest.Request,
        principal: Principal
    ): FriendshipApi.DeclineFriendshipRequest.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Friendship requests found successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
    )
    @Get("/friends/requests/")
    fun getFriendshipRequests(principal: Principal): FriendshipApi.GetFriendshipRequests.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Friendships found successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
    )
    @Get("/friends/")
    fun getFriendships(principal: Principal): FriendshipApi.GetFriendships.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Friendship messages found successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "404", description = "Friendship not found"),
    )
    @Get("/users/{friendUsername}/messages")
    fun getFriendshipMessages(
        @PathVariable friendUsername: String,
        @QueryValue from: Int,
        @QueryValue limit: Int,
        principal: Principal
    ): FriendshipApi.GetFriendshipMessages.Response

    @ApiResponses(
        ApiResponse(responseCode = "200", description = "Message sent successfully"),
        ApiResponse(responseCode = "400", description = "Bad request"),
        ApiResponse(responseCode = "401", description = "Unauthorized"),
        ApiResponse(responseCode = "404", description = "Friendship not found"),
    )
    @Post("/users/{friendUsername}/messages")
    fun sendMessage(
        @PathVariable friendUsername: String,
        @Body request: FriendshipApi.SendMessage.Request,
        principal: Principal
    ): FriendshipApi.SendMessage.Response
}
