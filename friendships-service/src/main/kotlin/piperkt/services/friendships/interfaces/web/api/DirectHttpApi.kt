package piperkt.services.friendships.interfaces.web.api

import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.PathVariable
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import piperkt.services.friendships.interfaces.web.api.interactions.FriendshipApi
import java.security.Principal

@Secured(SecurityRule.IS_AUTHENTICATED)
interface DirectHttpApi {

    fun getFriendshipMessages(
        @PathVariable friendUsername: String,
        principal: Principal
    ): FriendshipApi.GetFriendshipMessages.Response

    fun sendMessage(
        @PathVariable friendUsername: String,
        @Body request: FriendshipApi.SendMessage.Request,
        principal: Principal
    ): FriendshipApi.SendMessage.Response
}