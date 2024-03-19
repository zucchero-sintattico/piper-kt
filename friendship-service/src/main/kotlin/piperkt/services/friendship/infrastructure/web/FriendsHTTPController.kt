package piperkt.services.friendship.infrastructure.web

import io.micronaut.http.MediaType.TEXT_PLAIN
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.Post
import io.micronaut.http.annotation.Produces
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import io.micronaut.serde.annotation.Serdeable
import piperkt.services.friendship.application.FriendService

@Secured(SecurityRule.IS_AUTHENTICATED)
@Controller("/friend")
class FriendsHTTPController(private val friendService: FriendService) {

    @Serdeable data class SendFriendRequest(val action: String, val to: String)

    @Get("/")
    @Produces(TEXT_PLAIN)
    fun getFriends(authentication: Authentication): List<String> {
        val result = friendService.getFriends(authentication.name)
        return result
    }

    @Get("/request")
    @Produces(TEXT_PLAIN)
    fun getFriendRequests(authentication: Authentication): List<String> {
        val result = friendService.getFriendRequests(authentication.name)
        return result
    }

    @Post("/request")
    @Produces(TEXT_PLAIN)
    fun sendFriendRequest(authentication: Authentication, @Body request: SendFriendRequest) {
        when (request.action) {
            "send" -> friendService.sendFriendRequest(authentication.name, request.to)
            "accept" -> friendService.acceptFriendRequest(authentication.name, request.to)
            "deny" -> friendService.denyFriendRequest(authentication.name, request.to)
        }
    }
}
