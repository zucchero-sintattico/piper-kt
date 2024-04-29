package piperkt.services.friendships.interfaces.web.api

import io.micronaut.http.annotation.Body
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import piperkt.services.friendships.interfaces.web.api.interactions.FriendshipApi
import java.security.Principal

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
}