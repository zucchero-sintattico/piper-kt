package piperkt.services.friendships.interfaces.web.api

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.QueryValue
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import java.security.Principal
import piperkt.services.friendships.interfaces.web.api.interactions.FriendshipApi

@Secured(SecurityRule.IS_AUTHENTICATED)
interface FriendshipHttpApi {

    fun sendFriendshipRequest(
        @Body request: FriendshipApi.SendFriendshipRequest.Request,
        principal: Principal
    ): FriendshipApi.SendFriendshipRequest.Response

    fun acceptFriendshipRequest(
        @Body request: FriendshipApi.AcceptFriendshipRequest.Request,
        principal: Principal
    ): FriendshipApi.AcceptFriendshipRequest.Response

    fun declineFriendshipRequest(
        @Body request: FriendshipApi.DeclineFriendshipRequest.Request,
        principal: Principal
    ): FriendshipApi.DeclineFriendshipRequest.Response

    fun getFriendshipRequests(principal: Principal): FriendshipApi.GetFriendshipRequests.Response

    fun getFriendships(principal: Principal): FriendshipApi.GetFriendships.Response

    fun getFriendshipMessages(
        @PathVariable friendUsername: String,
        @QueryValue from: Int,
        @QueryValue limit: Int,
        principal: Principal
    ): FriendshipApi.GetFriendshipMessages.Response

    fun sendMessage(
        @PathVariable friendUsername: String,
        @Body request: FriendshipApi.SendMessage.Request,
        principal: Principal
    ): FriendshipApi.SendMessage.Response
}
