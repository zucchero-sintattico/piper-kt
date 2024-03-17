package piperkt.services.friendship.infrastructure.web

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import piperkt.services.friendship.application.FriendService

@Controller("/friend")
class FriendsHTTPController(private val friendService: FriendService) {

    private var testUserName = "user"

    @Get("/")
    fun getFriends(): List<String> {
        val result = friendService.getFriends(testUserName)
        // if user does not exist, return empty list
        return result
    }
}
